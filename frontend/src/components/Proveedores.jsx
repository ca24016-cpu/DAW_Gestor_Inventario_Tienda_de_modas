import { useEffect, useState } from "react";

function Proveedores() {

    const [proveedores, setProveedores] = useState([]);
    const [proveedorSeleccionado, setProveedorSeleccionado] = useState(null);
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        cargarProveedores();
    }, []);

    const cargarProveedores = async () => {
        try {
            const response = await fetch(
                "http://localhost:8080/api/proveedores"
            );

            const data = await response.json();

            data.sort((a, b) =>
                a.nombre.localeCompare(b.nombre)
            );

            setProveedores(data);

        } catch (error) {
            console.error(error);
        }
    };

    const seleccionarProveedor = async (id) => {

        try {

            const proveedorResponse =
                await fetch(
                    `http://localhost:8080/api/proveedores/${id}`
                );

            const proveedor =
                await proveedorResponse.json();

            setProveedorSeleccionado(proveedor);

            const productosResponse =
                await fetch(
                    `http://localhost:8080/api/producto-proveedor/proveedor/${id}`
                );

            const productosData =
                await productosResponse.json();

            setProductos(productosData);

        } catch (error) {
            console.error(error);
        }
    };

    return (
        <div style={{ display: "flex", gap: "30px" }}>

            <div style={{ width: "40%" }}>
                <h2>Directorio de Proveedores</h2>

                {proveedores.map((proveedor) => (
                    <div
                        key={proveedor.id}
                        onClick={() =>
                            seleccionarProveedor(proveedor.id)
                        }
                        style={{
                            cursor: "pointer",
                            padding: "10px",
                            borderBottom: "1px solid #ccc"
                        }}
                    >
                        {proveedor.nombre}
                    </div>
                ))}
            </div>

            <div style={{ width: "60%" }}>

                {proveedorSeleccionado && (
                    <>
                        <h2>
                            {proveedorSeleccionado.nombre}
                        </h2>

                        <p>
                            <strong>Contacto:</strong>{" "}
                            {proveedorSeleccionado.contacto}
                        </p>

                        <p>
                            <strong>Teléfono:</strong>{" "}
                            {proveedorSeleccionado.telefono}
                        </p>

                        <p>
                            <strong>Correo:</strong>{" "}
                            {proveedorSeleccionado.email}
                        </p>

                        <p>
                            <strong>Dirección:</strong>{" "}
                            {proveedorSeleccionado.direccion}
                        </p>

                        <h3>
                            Productos que suministra
                        </h3>

                        <ul>
                            {productos.map((producto) => (
                                <li key={producto.id}>
                                    <strong>
                                        {producto.productoNombre}
                                    </strong>

                                    <br />

                                    Costo:
                                    {" "}
                                    {producto.moneda}
                                    {" "}
                                    {producto.costo}

                                    <br />

                                    Entrega:
                                    {" "}
                                    {producto.tiempoEntrega}
                                    {" "}
                                    días
                                </li>
                            ))}
                        </ul>

                    </>
                )}

            </div>

        </div>
    );
}

export default Proveedores;
