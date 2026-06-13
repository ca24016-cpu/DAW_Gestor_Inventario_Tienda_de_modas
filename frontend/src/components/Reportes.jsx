import { useEffect, useState } from "react";
import GraficoInventario from "./GraficoInventario";

function Reportes() {

  const [inventario, setInventario] = useState([]);
  const [stockBajo, setStockBajo] = useState([]);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {

    try {

      const respuestaInventario =
        await fetch("http://localhost:8080/api/inventario");

      const datosInventario =
        await respuestaInventario.json();

      setInventario(datosInventario);

      const respuestaStockBajo =
        await fetch(
          "http://localhost:8080/api/inventario/bajo-stock/todos"
        );

      const datosStockBajo =
        await respuestaStockBajo.json();

      setStockBajo(datosStockBajo);

    } catch (error) {
      console.error(error);
    }
  };

  const totalProductos = inventario.length;

  const totalStock = inventario.reduce(
    (total, item) => total + item.cantidadActual,
    0
  );

  return (
    <div>

      <h2>Reportes de Inventario</h2>

      <h3>Resumen</h3>

      <p>
        Total de productos: {totalProductos}
      </p>

      <p>
        Total de unidades en stock: {totalStock}
      </p>

      <p>
        Productos con stock bajo: {stockBajo.length}
      </p>

      <GraficoInventario inventario={inventario} />

      <hr />
      
      <hr />

      <h3>Productos con Stock Bajo</h3>

      <ul>
        {stockBajo.map((producto) => (
          <li key={producto.id}>
            {producto.productoNombre}
            {" - "}
            Stock: {producto.cantidadActual}
          </li>
        ))}
      </ul>

      <hr />

      <h3>Inventario General</h3>

      <table border="1">
        <thead>
          <tr>
            <th>Producto</th>
            <th>Stock Actual</th>
            <th>Stock Mínimo</th>
            <th>Stock Máximo</th>
          </tr>
        </thead>

        <tbody>
          {inventario.map((item) => (
            <tr key={item.id}>
              <td>{item.productoNombre}</td>
              <td>{item.cantidadActual}</td>
              <td>{item.stockMinimo}</td>
              <td>{item.stockMaximo}</td>
            </tr>
          ))}
        </tbody>
      </table>

    </div>
  );
}

export default Reportes;