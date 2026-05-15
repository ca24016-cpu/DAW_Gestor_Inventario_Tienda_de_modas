import { useState, useEffect } from "react";
import "./Dashboard.css";

const Dashboard = () => {
  // Estados para almacenar los datos del JSON
  const [productos, setProductos] = useState([]);
  const [movimientos, setMovimientos] = useState([]);
  const [loading, setLoading] = useState(true);

  // Simulación de GET - Conectar con el JSON
  useEffect(() => {
    fetch('/MockDB.json')
      .then(response => {
        if (!response.ok) throw new Error("Error al cargar datos");
        return response.json();
      })
      .then(data => {
        setProductos(data.productos);
        setMovimientos(data.movimientos);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error:", error);
        setLoading(false);
      });
  }, []);

  // Cálculo de KPIs 
  const totalAlmacen = productos.reduce(
    (acc, prod) => acc + prod.inventario.cantidad_actual, 0
  );

  const stockBajo = productos.filter(
    prod => prod.inventario.cantidad_actual <= prod.inventario.stock_minimo
  );

  const totalVentas = movimientos
    .filter(mov => mov.tipo === "SALIDA")
    .reduce((acc, mov) => acc + mov.cantidad, 0);

  // Preparar datos para la gráfica 
  const datosGrafica = movimientos.slice(0, 7);
  const maxValor = Math.max(...datosGrafica.map(m => m.cantidad), 1);

  if (loading) return (
  <div className="skeleton-container">
    <div className="skeleton-card"></div>
    <div className="skeleton-card"></div>
    <div className="skeleton-card"></div>
    <div className="skeleton-chart"></div>
  </div>
);

  return (
    <div className="dashboard-container">
      <header className="dashboard-header">
        <h1>FashionTrack</h1>
        <p>Panel Principal - Resumen Operativo</p>
      </header>

      {/* KPIs - Tarjetas de Resumen */}
      <section className="kpi-grid">
        <article className="kpi-card">
          <h3>Total en Almacén</h3>
          <p className="kpi-value">{totalAlmacen}</p>
          <span className="kpi-icon">📦</span>
        </article>

        <article className="kpi-card">
          <h3>Ventas (Salidas)</h3>
          <p className="kpi-value">{totalVentas}</p>
          <span className="kpi-icon">💰</span>
        </article>

        <article className="kpi-card alert">
          <h3>Stock Bajo</h3>
          <p className="kpi-value">{stockBajo.length}</p>
          <span className="kpi-icon">⚠️</span>
        </article>
      </section>

      {/* Gráfica de Movimientos */}
      <section className="chart-section">
        <h2>Últimos Movimientos</h2>
        <div className="bar-chart">
          {datosGrafica.map((m) => (
            <div key={m.id_movimiento} className="chart-group">
              <div className="bar-container">
                <div
                  className={`bar ${m.tipo === 'ENTRADA' ? 'entrada' : 'salida'}`}
                  style={{ height: `${(m.cantidad / maxValor) * 100}%` }}
                  title={`${m.tipo}: ${m.cantidad}`}
                ></div>
              </div>
              <span className="chart-label">{m.fecha.slice(5)}</span>
            </div>
          ))}
        </div>
        <div className="chart-legend">
          <span className="legend entrada">Entradas</span>
          <span className="legend salida">Salidas</span>
        </div>
      </section>
    </div>
  );
};

export default Dashboard;