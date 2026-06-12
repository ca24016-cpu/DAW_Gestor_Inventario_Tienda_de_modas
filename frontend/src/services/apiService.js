
const API_URL = 'http://localhost:8080/api/productos';

export async function obtenerProductos() {
    try {
        const respuesta = await fetch(API_URL);
        if (!respuesta.ok) throw new Error('Error al conectar con el servidor Java');
        return await respuesta.json();
    } catch (error) {
        console.error('Error en obtenerProductos:', error);
        return [];
    }
}

// DELETE - Eliminar un producto por su ID
export async function eliminarProductoAPI(id) {
    try {
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
        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(productoEditado)
        });
        return respuesta.ok;
    } catch (error) {
        console.error('Error en actualizarProductoAPI:', error);
        return false;
    }
}

/**
 * POST - Guarda una nueva prenda en el servidor de Java
 */
export async function crearProductoAPI(nuevoProducto) {
    try {
        const respuesta = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(nuevoProducto)
        });
        if (!respuesta.ok) throw new Error('No se pudo guardar el producto en el servidor');
        return await respuesta.json(); // Retorna el producto guardado con su ID real de la Base de Datos
    } catch (error) {
        console.error('Error en crearProductoAPI:', error);
        return null;
    }
}