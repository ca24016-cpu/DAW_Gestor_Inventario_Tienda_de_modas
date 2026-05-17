import { useState, useEffect } from "react";
import "./ProductoLista.css";

const ProductoLista = () => {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filtro, setFiltro] = useState("TODOS");

  // GET - Cargar productos desde MockDB
  useEffect(() => {
    fetch("/MockDB.json")
      .then((response) => {
        if (!response.ok) throw new Error("Error al cargar productos");
        return response.json();
      })
      .then((data) => {
        setProductos(data.productos);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error al cargar productos:", error);
        setLoading(false);
      });
  }, []);

  // Filtrar productos por estado
  const productosFiltrados =
    filtro === "TODOS"
      ? productos
      : productos.filter((prod) => prod.estado === filtro);

  // Estado del badge
  const getEstadoBadge = (estado) => {
    return estado === "ACTIVO" ? "activo" : "inactivo";
  };

  // Verificar stock
  const getStockClass = (producto) => {
    const { cantidad_actual, stock_minimo } = producto.inventario;
    if (cantidad_actual === 0) return "sin-stock";
    if (cantidad_actual <= stock_minimo) return "stock-bajo";
    return "stock-ok";
  };

  if (loading) {
    return (
      <div className="producto-lista-container">
        <div className="skeleton-table">
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
        </div>
      </div>
    );
  }

  return (
    <div className="producto-lista-container">
      <div className="lista-header">
        <h2>Catálogo de Productos</h2>
        <div className="filtro-grupo">
          <button
            className={`btn-filtro ${filtro === "TODOS" ? "activo" : ""}`}
            onClick={() => setFiltro("TODOS")}
          >
            Todos ({productos.length})
          </button>
          <button
            className={`btn-filtro ${filtro === "ACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("ACTIVO")}
          >
            Activos ({productos.filter((p) => p.estado === "ACTIVO").length})
          </button>
          <button
            className={`btn-filtro ${filtro === "INACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("INACTIVO")}
          >
            Inactivos ({productos.filter((p) => p.estado === "INACTIVO").length})
          </button>
        </div>
      </div>

      {productosFiltrados.length === 0 ? (
        <div className="sin-datos">
          <p>No hay productos para mostrar</p>
        </div>
      ) : (
        <div className="tabla-responsiva">
          <table className="tabla-productos">
            <thead>
              <tr>
                <th>SKU</th>
                <th>Nombre</th>
                <th>Talla</th>
                <th>Precio</th>
                <th>Stock</th>
                <th>Estado</th>
                <th>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {productosFiltrados.map((producto) => (
                <tr key={producto.id_producto} className="tabla-fila">
                  <td data-label="SKU" className="sku">
                    <code>{producto.sku}</code>
                  </td>
                  <td data-label="Nombre">
                    <div className="producto-info">
                      <strong>{producto.nombre}</strong>
                      <span className="descripcion">
                        {producto.descripcion}
                      </span>
                    </div>
                  </td>
                  <td data-label="Talla">
                    <span className="badge-talla">{producto.talla.nombre}</span>
                  </td>
                  <td data-label="Precio" className="precio">
                    ${producto.precio_unitario.toFixed(2)}
                  </td>
                  <td
                    data-label="Stock"
                    className={`stock ${getStockClass(producto)}`}
                  >
                    <span className="cantidad">
                      {producto.inventario.cantidad_actual}
                    </span>
                    <span className="stock-info">
                      Min: {producto.inventario.stock_minimo}
                    </span>
                  </td>
                  <td data-label="Estado">
                    <span
                      className={`badge-estado ${getEstadoBadge(
                        producto.estado
                      )}`}
                    >
                      {producto.estado}
                    </span>
                  </td>
                  <td data-label="Acciones" className="acciones">
                    <button className="btn-ver" title="Ver detalles">
                      👁️
                    </button>
                    <button className="btn-editar" title="Editar">
                      ✏️
                    </button>
                    <button className="btn-eliminar" title="Eliminar">
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      <div className="estadisticas-lista">
        <div className="stat">
          <span className="stat-label">Total de productos:</span>
          <span className="stat-valor">{productos.length}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Valor total en almacén:</span>
          <span className="stat-valor">
            $
            {productos
              .reduce(
                (acc, prod) =>
                  acc +
                  prod.precio_unitario * prod.inventario.cantidad_actual,
                0
              )
              .toFixed(2)}
          </span>
        </div>
        <div className="stat">
          <span className="stat-label">Productos en stock bajo:</span>
          <span className="stat-valor stat-alerta">
            {
              productos.filter(
                (p) =>
                  p.inventario.cantidad_actual <= p.inventario.stock_minimo
              ).length
            }
          </span>
        </div>
      </div>
    </div>
  );
};

export default ProductoLista;
