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

// ==========================================
// APIS PARA CATEGORÍAS
// ==========================================

export async function obtenerCategorias() {
    try {
        const response = await fetch('http://localhost:8080/api/categorias');
        if (!response.ok) throw new Error('Error al conectar con la API de categorías');
        const data = await response.json();
        return data.map(c => ({
            id_categoria: c.id,
            nombre: c.nombre,
            descripcion: c.descripcion,
            id_categoria_padre: c.categoriaPadreId,
            activo: c.activo
        }));
    } catch (error) {
        console.error('Error en obtenerCategorias:', error);
        return [];
    }
}

export async function crearCategoriaAPI(nueva) {
    try {
        const backendCat = {
            nombre: nueva.nombre,
            descripcion: nueva.descripcion,
            categoriaPadreId: nueva.id_categoria_padre || null,
            activo: nueva.activo,
            nivel: nueva.id_categoria_padre ? 2 : 1
        };
        const response = await fetch('http://localhost:8080/api/categorias', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(backendCat)
        });
        if (!response.ok) throw new Error('No se pudo guardar la categoría');
        const c = await response.json();
        return {
            id_categoria: c.id,
            nombre: c.nombre,
            descripcion: c.descripcion,
            id_categoria_padre: c.categoriaPadreId,
            activo: c.activo
        };
    } catch (error) {
        console.error('Error en crearCategoriaAPI:', error);
        return null;
    }
}

export async function actualizarCategoriaAPI(id, editada) {
    try {
        const backendCat = {
            id: editada.id_categoria,
            nombre: editada.nombre,
            descripcion: editada.descripcion,
            categoriaPadreId: editada.id_categoria_padre || null,
            activo: editada.activo,
            nivel: editada.id_categoria_padre ? 2 : 1
        };
        const response = await fetch(`http://localhost:8080/api/categorias/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(backendCat)
        });
        return response.ok;
    } catch (error) {
        console.error('Error en actualizarCategoriaAPI:', error);
        return false;
    }
}

export async function eliminarCategoriaAPI(id) {
    try {
        const response = await fetch(`http://localhost:8080/api/categorias/${id}`, {
            method: 'DELETE'
        });
        return response.ok;
    } catch (error) {
        console.error('Error en eliminarCategoriaAPI:', error);
        return false;
    }
}

// ==========================================
// APIS PARA PROVEEDORES
// ==========================================

export async function obtenerProveedores() {
    try {
        const response = await fetch('http://localhost:8080/api/proveedores');
        if (!response.ok) throw new Error('Error al conectar con la API de proveedores');
        const data = await response.json();
        return data.map(p => ({
            id_proveedor: p.id,
            nombre: p.nombre,
            contacto: p.contacto,
            telefono: p.telefono,
            email: p.email,
            direccion: p.direccion,
            activo: p.activo
        }));
    } catch (error) {
        console.error('Error en obtenerProveedores:', error);
        return [];
    }
}

export async function crearProveedorAPI(nuevo) {
    try {
        const backendProv = {
            nombre: nuevo.nombre,
            contacto: nuevo.contacto,
            telefono: nuevo.telefono,
            email: nuevo.email,
            direccion: nuevo.direccion,
            activo: nuevo.activo
        };
        const response = await fetch('http://localhost:8080/api/proveedores', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(backendProv)
        });
        if (!response.ok) throw new Error('No se pudo guardar el proveedor');
        const p = await response.json();
        return {
            id_proveedor: p.id,
            nombre: p.nombre,
            contacto: p.contacto,
            telefono: p.telefono,
            email: p.email,
            direccion: p.direccion,
            activo: p.activo
        };
    } catch (error) {
        console.error('Error en crearProveedorAPI:', error);
        return null;
    }
}

export async function actualizarProveedorAPI(id, editado) {
    try {
        const backendProv = {
            id: editado.id_proveedor,
            nombre: editado.nombre,
            contacto: editado.contacto,
            telefono: editado.telefono,
            email: editado.email,
            direccion: editado.direccion,
            activo: editado.activo
        };
        const response = await fetch(`http://localhost:8080/api/proveedores/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(backendProv)
        });
        return response.ok;
    } catch (error) {
        console.error('Error en actualizarProveedorAPI:', error);
        return false;
    }
}

export async function eliminarProveedorAPI(id) {
    try {
        const response = await fetch(`http://localhost:8080/api/proveedores/${id}`, {
            method: 'DELETE'
        });
        return response.ok;
    } catch (error) {
        console.error('Error en eliminarProveedorAPI:', error);
        return false;
    }
}