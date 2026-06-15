const API_URL = 'http://localhost:8080/api/productos';
const INVENTARIO_URL = 'http://localhost:8080/api/inventario';

// GET - Cargar y formatear productos (uniendo con inventario)
export async function obtenerProductos() {
    try {
        const [respProd, respInv] = await Promise.all([
            fetch(API_URL),
            fetch(INVENTARIO_URL)
        ]);

        if (!respProd.ok) throw new Error('Error al conectar con el API de productos');
        if (!respInv.ok) throw new Error('Error al conectar con el API de inventario');

        const productosData = await respProd.json();
        const inventarioData = await respInv.json();

        const productosList = Array.isArray(productosData) ? productosData : (productosData.value || []);
        const inventarioList = Array.isArray(inventarioData) ? inventarioData : (inventarioData.value || []);

        const productosMapeados = productosList.map(p => {
            const inv = inventarioList.find(item => item.productoId === p.id);
            return {
                id_producto: p.id,
                sku: p.sku,
                nombre: p.nombre,
                descripcion: p.descripcion,
                precio_unitario: p.precioUnitario || 0,
                estado: p.estado || "ACTIVO",
                talla: { 
                    id_talla: p.tallaId || 0, 
                    nombre: p.tallaNombre || "N/A" 
                },
                inventario: {
                    cantidad_actual: inv ? inv.cantidadActual : 0,
                    stock_minimo: inv ? inv.stockMinimo : 0,
                    stock_maximo: inv ? inv.stockMaximo : 0
                }
            };
        });

        return productosMapeados;
    } catch (error) {
        console.error('Error en obtenerProductos:', error);
        return [];
    }
}

// DELETE - Eliminar un producto por su ID (eliminando primero su inventario si existe)
export async function eliminarProductoAPI(id) {
    try {
        const respInv = await fetch(INVENTARIO_URL);
        if (respInv.ok) {
            const inventarioData = await respInv.json();
            const inventarioList = Array.isArray(inventarioData) ? inventarioData : (inventarioData.value || []);
            const invAsociado = inventarioList.find(item => item.productoId === id);
            
            if (invAsociado) {
                await fetch(`${INVENTARIO_URL}/${invAsociado.id}`, {
                    method: 'DELETE'
                });
            }
        }

        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: 'DELETE'
        });
        return respuesta.ok; 
    } catch (error) {
        console.error('Error en eliminarProductoAPI:', error);
        return false;
    }
}

// PUT - Actualizar los datos modificados de un producto
export async function actualizarProductoAPI(id, productoEditado) {
    try {
        const backendProduct = {
            id: productoEditado.id_producto,
            nombre: productoEditado.nombre,
            descripcion: productoEditado.descripcion,
            sku: productoEditado.sku,
            precioUnitario: productoEditado.precio_unitario,
            estado: productoEditado.estado,
            tallaId: productoEditado.talla ? (productoEditado.talla.id_talla || productoEditado.talla.id) : null
        };

        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(backendProduct)
        });
        return respuesta.ok;
    } catch (error) {
        console.error('Error en actualizarProductoAPI:', error);
        return false;
    }
}

// POST - Guarda una nueva prenda y su inventario
export async function crearProductoAPI(nuevoProducto) {
    try {
        const backendProduct = {
            nombre: nuevoProducto.nombre,
            descripcion: nuevoProducto.descripcion,
            sku: nuevoProducto.sku,
            precioUnitario: nuevoProducto.precio_unitario,
            estado: nuevoProducto.estado,
            tallaId: nuevoProducto.talla ? (nuevoProducto.talla.id_talla || nuevoProducto.talla.id) : null
        };

        const respProd = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(backendProduct)
        });

        if (!respProd.ok) throw new Error('No se pudo guardar el producto en el servidor');
        const savedProduct = await respProd.json();

        const backendInventory = {
            productoId: savedProduct.id,
            cantidadActual: nuevoProducto.inventario?.cantidad_actual || 0,
            stockMinimo: nuevoProducto.inventario?.stock_minimo || 0,
            stockMaximo: nuevoProducto.inventario?.stock_maximo || 100
        };

        const respInv = await fetch(INVENTARIO_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(backendInventory)
        });

        const inventarioRegistrado = respInv.ok ? await respInv.json() : null;

        return {
            id_producto: savedProduct.id,
            sku: savedProduct.sku,
            nombre: savedProduct.nombre,
            descripcion: savedProduct.descripcion,
            precio_unitario: savedProduct.precioUnitario,
            estado: savedProduct.estado,
            talla: { 
                id_talla: savedProduct.tallaId || 0, 
                nombre: savedProduct.tallaNombre || "N/A" 
            },
            inventario: {
                cantidad_actual: inventarioRegistrado ? inventarioRegistrado.cantidadActual : backendInventory.cantidadActual,
                stock_minimo: inventarioRegistrado ? inventarioRegistrado.stockMinimo : backendInventory.stockMinimo,
                stock_maximo: inventarioRegistrado ? inventarioRegistrado.stockMaximo : backendInventory.stockMaximo
            }
        };
    } catch (error) {
        console.error('Error en crearProductoAPI:', error);
        return null;
    }
}