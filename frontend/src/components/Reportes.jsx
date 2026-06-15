import { useEffect, useState } from "react";
import GraficoInventario from "./GraficoInventario";

function Reportes() {

  const [inventario, setInventario] = useState([]);
  const [stockBajo, setStockBajo] = useState([]);
  const [errorApi, setErrorApi] = useState(null);

  useEffect(() => {
    cargarDatos();
  }, []);

  const cargarDatos = async () => {
    try {
      const respuestaInventario = await fetch("http://localhost:8080/api/inventario");
      if (!respuestaInventario.ok) throw new Error("Error al obtener inventario");
      const datosInventario = await respuestaInventario.json();
      setInventario(Array.isArray(datosInventario) ? datosInventario : []);

      const respuestaStockBajo = await fetch("http://localhost:8080/api/inventario/bajo-stock/todos");
      if (!respuestaStockBajo.ok) throw new Error("Error al obtener stock bajo");
      const datosStockBajo = await respuestaStockBajo.json();
      setStockBajo(Array.isArray(datosStockBajo) ? datosStockBajo : []);
      setErrorApi(null);
    } catch (error) {
      console.error("Error al cargar datos de reportes:", error);
      setErrorApi("No se pudo conectar con el servidor de inventario o la base de datos.");
    }
  };

  const totalProductos = Array.isArray(inventario) ? inventario.length : 0;

  const totalStock = Array.isArray(inventario)
    ? inventario.reduce((total, item) => total + (item.cantidadActual || 0), 0)
    : 0;

  return (
    <div style={{ padding: "20px", maxWidth: "1000px", margin: "0 auto" }}>
      <h2>Reportes de Inventario</h2>

      {errorApi && (
        <div style={{
          background: "#f8d7da",
          color: "#721c24",
          padding: "15px",
          borderRadius: "8px",
          border: "1px solid #f5c6cb",
          marginBottom: "20px"
        }}>
          ⚠️ <strong>Error de conexión:</strong> {errorApi}
        </div>
      )}

      <h3>Resumen</h3>
      <div style={{ display: "grid", gridTemplateColumns: "repeat(auto-fit, minmax(200px, 1fr))", gap: "15px", marginBottom: "30px" }}>
        <div style={{ background: "#f8f9fa", padding: "15px", borderRadius: "8px", borderLeft: "4px solid var(--primary)" }}>
          <span style={{ color: "var(--text-muted)", fontSize: "0.9rem" }}>Total de productos:</span>
          <h4 style={{ margin: "5px 0 0 0", fontSize: "1.4rem", color: "var(--primary)" }}>{totalProductos}</h4>
        </div>
        <div style={{ background: "#f8f9fa", padding: "15px", borderRadius: "8px", borderLeft: "4px solid var(--green)" }}>
          <span style={{ color: "var(--text-muted)", fontSize: "0.9rem" }}>Total de unidades en stock:</span>
          <h4 style={{ margin: "5px 0 0 0", fontSize: "1.4rem", color: "var(--green)" }}>{totalStock}</h4>
        </div>
        <div style={{ background: "#f8f9fa", padding: "15px", borderRadius: "8px", borderLeft: "4px solid var(--red)" }}>
          <span style={{ color: "var(--text-muted)", fontSize: "0.9rem" }}>Productos con stock bajo:</span>
          <h4 style={{ margin: "5px 0 0 0", fontSize: "1.4rem", color: "var(--red)" }}>{Array.isArray(stockBajo) ? stockBajo.length : 0}</h4>
        </div>
      </div>

      {Array.isArray(inventario) && inventario.length > 0 && (
        <GraficoInventario inventario={inventario} />
      )}

      <hr style={{ border: "0", borderTop: "1px solid #e9ecef", margin: "30px 0" }} />

      <h3>Productos con Stock Bajo</h3>
      {Array.isArray(stockBajo) && stockBajo.length === 0 ? (
        <p style={{ color: "var(--text-muted)" }}>No hay productos con stock bajo en este momento.</p>
      ) : (
        <ul style={{ listStyleType: "none", padding: 0 }}>
          {Array.isArray(stockBajo) && stockBajo.map((producto) => (
            <li key={producto.id} style={{ background: "#fff5f5", color: "#c0392b", padding: "10px 15px", borderRadius: "6px", marginBottom: "8px", borderLeft: "4px solid #e74c3c" }}>
              <strong>{producto.productoNombre || "Prenda sin nombre"}</strong> - Stock Actual: {producto.cantidadActual} (Mínimo requerido: {producto.stockMinimo})
            </li>
          ))}
        </ul>
      )}

      <hr style={{ border: "0", borderTop: "1px solid #e9ecef", margin: "30px 0" }} />

      <h3>Inventario General</h3>
      {Array.isArray(inventario) && inventario.length === 0 ? (
        <p style={{ color: "var(--text-muted)" }}>No hay datos de inventario disponibles.</p>
      ) : (
        <div style={{ overflowX: "auto" }}>
          <table style={{ width: "100%", borderCollapse: "collapse", fontSize: "0.95rem" }}>
            <thead>
              <tr style={{ background: "#f8f9fa", borderBottom: "2px solid var(--primary)" }}>
                <th style={{ padding: "12px", textAlign: "left", color: "var(--primary)" }}>Producto</th>
                <th style={{ padding: "12px", textAlign: "left", color: "var(--primary)" }}>Stock Actual</th>
                <th style={{ padding: "12px", textAlign: "left", color: "var(--primary)" }}>Stock Mínimo</th>
                <th style={{ padding: "12px", textAlign: "left", color: "var(--primary)" }}>Stock Máximo</th>
              </tr>
            </thead>
            <tbody>
              {Array.isArray(inventario) && inventario.map((item) => (
                <tr key={item.id} style={{ borderBottom: "1px solid #e9ecef" }}>
                  <td style={{ padding: "12px" }}>{item.productoNombre || "Sin nombre"}</td>
                  <td style={{ padding: "12px", fontWeight: "600" }}>{item.cantidadActual}</td>
                  <td style={{ padding: "12px" }}>{item.stockMinimo}</td>
                  <td style={{ padding: "12px" }}>{item.stockMaximo}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>

  );
}

export default Reportes;