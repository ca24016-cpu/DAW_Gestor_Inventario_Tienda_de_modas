import { useState } from "react";
import ProductoLista from "./ProductoLista";
import FormularioProducto from "./FormularioProducto";
import "./GestorProductos.css";

const GestorProductos = () => {
  const [tab, setTab] = useState("lista");
  const [productoAgregado, setProductoAgregado] = useState(null);

  const handleProductoAgregado = (producto) => {
    setProductoAgregado(producto);
    // Mostrar notificación breve
    setTimeout(() => setProductoAgregado(null), 3000);
  };

  return (
    <div className="gestor-productos-container">
      {/* Notificación flotante de producto agregado */}
      {productoAgregado && (
        <div className="notificacion-flotante">
          <div className="notif-content">
            <span className="notif-icon">✅</span>
            <div>
              <strong>¡Producto agregado!</strong>
              <p>{productoAgregado.nombre}</p>
            </div>
          </div>
        </div>
      )}

      {/* Navegación de tabs */}
      <div className="tabs-navegacion">
        <button
          className={`tab-btn ${tab === "lista" ? "activo" : ""}`}
          onClick={() => setTab("lista")}
        >
          <span className="tab-icon">📋</span>
          Listado de Productos
        </button>
        <button
          className={`tab-btn ${tab === "agregar" ? "activo" : ""}`}
          onClick={() => setTab("agregar")}
        >
          <span className="tab-icon">➕</span>
          Agregar Producto
        </button>
      </div>

      {/* Contenido de tabs */}
      <div className="tabs-contenido">
        {tab === "lista" && <ProductoLista />}
        {tab === "agregar" && (
          <FormularioProducto onProductoAgregado={handleProductoAgregado} />
        )}
      </div>
    </div>
  );
};

export default GestorProductos;
