import { useState, useEffect } from "react";
import "./Dashboard.css";

const Dashboard = () => {
  // Estados para almacenar los datos del JSON
  const [productos, setProductos] = useState([]);
  const [movimientos, setMovimientos] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      fetch('http://localhost:8080/api/productos'),
      fetch('http://localhost:8080/api/inventario'),
      fetch('http://localhost:8080/api/entrada-stock'),
      fetch('http://localhost:8080/api/salida-stock')
    ])
      .then(async ([respProd, respInv, respEnt, respSal]) => {
        if (!respProd.ok || !respInv.ok || !respEnt.ok || !respSal.ok) {
          throw new Error("Error fetching data from API");
        }
        const prods = await respProd.json();
        const invs = await respInv.json();
        const ents = await respEnt.json();
        const sals = await respSal.json();

        const prodList = Array.isArray(prods) ? prods : (prods.value || []);
        const invList = Array.isArray(invs) ? invs : (invs.value || []);
        const entList = Array.isArray(ents) ? ents : (ents.value || []);
        const salList = Array.isArray(sals) ? sals : (sals.value || []);

        const mappedProducts = prodList.map(p => {
          const inv = invList.find(i => i.productoId === p.id);
          return {
            id_producto: p.id,
            nombre: p.nombre,
            inventario: {
              cantidad_actual: inv ? inv.cantidadActual : 0,
              stock_minimo: inv ? inv.stockMinimo : 0,
              stock_maximo: inv ? inv.stockMaximo : 0
            }
          };
        });

        const mappedEnts = entList.map(e => ({
          id_movimiento: `ent-${e.id}`,
          tipo: "ENTRADA",
          cantidad: e.cantidad,
          fecha: e.fechaEntrada ? e.fechaEntrada.slice(0, 10) : ""
        }));

        const mappedSals = salList.map(s => ({
          id_movimiento: `sal-${s.id}`,
          tipo: "SALIDA",
          cantidad: s.cantidad,
          fecha: s.fechaSalida ? s.fechaSalida.slice(0, 10) : ""
        }));

        const combinedMovements = [...mappedEnts, ...mappedSals].sort((a, b) => 
          new Date(b.fecha) - new Date(a.fecha)
        );

        setProductos(mappedProducts);
        setMovimientos(combinedMovements);
        setLoading(false);
      })
      .catch(error => {
        console.error("Error loading dashboard data:", error);
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