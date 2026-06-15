import { useState, useEffect } from "react";
import "./ProductoLista.css";
import { 
  obtenerProductos, 
  eliminarProductoAPI, 
  actualizarProductoAPI, 
  obtenerCategorias 
} from "../../services/apiService";

const ProductoLista = () => {
  const [productos, setProductos] = useState([]);
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [filtro, setFiltro] = useState("TODOS");
  const [busqueda, setBusqueda] = useState("");
  const [categoriaFiltro, setCategoriaFiltro] = useState("TODAS");

  const [productoEditando, setProductoEditando] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);

  const [productoDetalle, setProductoDetalle] = useState(null);
  const [mostrarModalDetalle, setMostrarModalDetalle] = useState(false);

  const tallas = [
    { id: 10, nombre: "S", categoria: "INFANTIL" },
    { id: 14, nombre: "M", categoria: "ADULTO" },
    { id: 15, nombre: "L", categoria: "ADULTO" },
    { id: 16, nombre: "XL", categoria: "ADULTO" },
  ];

  // GET - Cargar productos y categorías reales desde el Backend
  useEffect(() => {
    Promise.all([obtenerProductos(), obtenerCategorias()])
      .then(([prodData, catData]) => {
        const lista = Array.isArray(prodData) ? prodData : (prodData.productos || []);
        setProductos(lista);
        setCategorias(catData || []);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error al cargar datos en ProductoLista:", error);
        setLoading(false);
      });
  }, []);

  // Productos que coinciden con búsqueda y categoría (antes de filtrar por estado)
  const productosFiltradosPorBusquedaYCategoria = productos.filter((prod) => {
    const cumpleBusqueda =
      prod.nombre.toLowerCase().includes(busqueda.toLowerCase()) ||
      prod.sku.toLowerCase().includes(busqueda.toLowerCase());

    let cumpleCategoria = true;
    if (categoriaFiltro !== "TODAS") {
      const selectedId = parseInt(categoriaFiltro);
      // Encontrar todas las subcategorías de esta categoría (hijos)
      const hijosIds = categorias
        .filter((c) => c.id_categoria_padre === selectedId)
        .map((c) => c.id_categoria);
      
      // El producto debe tener la categoría seleccionada o ser de una de sus subcategorías
      cumpleCategoria = prod.categorias && prod.categorias.some(
        (c) => c.id_categoria === selectedId || hijosIds.includes(c.id_categoria)
      );
    }

    return cumpleBusqueda && cumpleCategoria;
  });

  // Filtrado final de productos por estado
  const productosFiltrados = productosFiltradosPorBusquedaYCategoria.filter((prod) => {
    return filtro === "TODOS" ? true : prod.estado === filtro;
  });

  const totalCoincidencias = productosFiltradosPorBusquedaYCategoria.length;
  const activosCoincidencias = productosFiltradosPorBusquedaYCategoria.filter(p => p.estado === "ACTIVO").length;
  const inactivosCoincidencias = productosFiltradosPorBusquedaYCategoria.filter(p => p.estado === "INACTIVO").length;

  const hayFiltrosActivos = busqueda !== "" || categoriaFiltro !== "TODAS" || filtro !== "TODOS";

  const limpiarFiltros = () => {
    setBusqueda("");
    setCategoriaFiltro("TODAS");
    setFiltro("TODOS");
  };

  const verDetalles = (producto) => {
    setProductoDetalle(producto);
    setMostrarModalDetalle(true);
  };

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

  const eliminarProducto = async (id) => {
    const confirmar = window.confirm("¿Está seguro de eliminar este producto?");
    if (confirmar) {
      const eliminado = await eliminarProductoAPI(id);
      if (eliminado) {
        setProductos((prev) => prev.filter((p) => p.id_producto !== id));
      } else {
        alert("No se pudo eliminar en el servidor.");
      }
    }
  };

  const editarProducto = (producto) => {
    setProductoEditando({ ...producto });
    setMostrarModal(true);
  };

  const guardarCambios = async () => {
    const actualizado = await actualizarProductoAPI(productoEditando.id_producto, productoEditando);
    if (actualizado) {
      setProductos((prev) =>
        prev.map((p) => (p.id_producto === productoEditando.id_producto ? productoEditando : p))
      );
      setMostrarModal(false);
    } else {
      alert("No se pudieron guardar los cambios en el servidor.");
    }
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
      {/* Encabezado */}
      <div className="lista-header">
        <h2>Catálogo de Productos</h2>
      </div>

      {/* Controles de Búsqueda y Categoría */}
      <div className="lista-controles-productos">
        <div className="control-busqueda">
          <input
            type="text"
            placeholder="🔍 Buscar por nombre o SKU..."
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
            className="input-busqueda-prod"
          />
        </div>
        
        <div className="control-categoria">
          <select
            value={categoriaFiltro}
            onChange={(e) => setCategoriaFiltro(e.target.value)}
            className="select-filtro-categoria"
          >
            <option value="TODAS">Filtrar por Categoría: Todas</option>
            {categorias
              .filter((c) => !c.id_categoria_padre) // Categorías principales (Padres)
              .map((padre) => (
                <optgroup key={padre.id_categoria} label={padre.nombre}>
                  <option value={padre.id_categoria}>
                    {padre.nombre} (Principal)
                  </option>
                  {categorias
                    .filter((c) => c.id_categoria_padre === padre.id_categoria) // Subcategorías (Hijos)
                    .map((hijo) => (
                      <option key={hijo.id_categoria} value={hijo.id_categoria}>
                        └─ {hijo.nombre}
                      </option>
                    ))}
                </optgroup>
              ))}
          </select>
        </div>

        <div className="filtro-grupo">
          <button
            className={`btn-filtro ${filtro === "TODOS" ? "activo" : ""}`}
            onClick={() => setFiltro("TODOS")}
          >
            Todos ({totalCoincidencias})
          </button>
          <button
            className={`btn-filtro ${filtro === "ACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("ACTIVO")}
          >
            Activos ({activosCoincidencias})
          </button>
          <button
            className={`btn-filtro ${filtro === "INACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("INACTIVO")}
          >
            Inactivos ({inactivosCoincidencias})
          </button>
        </div>

        {hayFiltrosActivos && (
          <button className="btn-limpiar-filtros" onClick={limpiarFiltros} title="Limpiar todos los filtros">
            🧹 Limpiar
          </button>
        )}
      </div>

      {/* Tabla */}
      {productosFiltrados.length === 0 ? (
        <div className="sin-datos">
          <p>No hay productos para mostrar con los filtros aplicados</p>
        </div>
      ) : (
        <div className="tabla-responsiva">
          <table className="tabla-productos">
            <thead>
              <tr>
                <th>SKU</th>
                <th>Nombre</th>
                <th>Categorías</th>
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
                  <td data-label="Categorías">
                    <div className="producto-categorias-tags">
                      {producto.categorias && producto.categorias.length > 0 ? (
                        producto.categorias.map((c) => (
                          <span key={c.id_categoria} className="badge-categoria-tag">
                            {c.nombre}
                          </span>
                        ))
                      ) : (
                        <span className="sin-categoria">Ninguna</span>
                      )}
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
                    <button 
                      className="btn-ver" 
                      title="Ver detalles"
                      onClick={() => verDetalles(producto)}
                    >
                      👁️
                    </button>
                    <button
                      className="btn-editar"
                      title="Editar"
                      onClick={() => editarProducto(producto)}
                    >
                      ✏️
                    </button>
                    <button
                      className="btn-eliminar"
                      title="Eliminar"
                      onClick={() => eliminarProducto(producto.id_producto)}
                    >
                      🗑️
                    </button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {/* Modal Editar */}
      {mostrarModal && productoEditando && (
        <div className="modal-overlay">
          <div className="modal-editar">
            <div className="modal-header">
              <h2>Editar Producto</h2>
              <button
                className="btn-cerrar-modal"
                onClick={() => setMostrarModal(false)}
              >
                ✕
              </button>
            </div>
            <div className="modal-form">
              <div className="modal-grupo">
                <label>Nombre del Producto *</label>
                <input
                  type="text"
                  value={productoEditando.nombre}
                  onChange={(e) =>
                    setProductoEditando({
                      ...productoEditando,
                      nombre: e.target.value,
                    })
                  }
                  required
                />
              </div>

              <div className="form-row">
                <div className="modal-grupo">
                  <label>SKU (Código) *</label>
                  <input
                    type="text"
                    value={productoEditando.sku}
                    onChange={(e) =>
                      setProductoEditando({
                        ...productoEditando,
                        sku: e.target.value,
                      })
                    }
                    required
                  />
                </div>

                <div className="modal-grupo">
                  <label>Precio Unitario ($) *</label>
                  <input
                    type="number"
                    step="0.01"
                    min="0"
                    value={productoEditando.precio_unitario}
                    onChange={(e) =>
                      setProductoEditando({
                        ...productoEditando,
                        precio_unitario: parseFloat(e.target.value) || 0,
                      })
                    }
                    required
                  />
                </div>
              </div>

              <div className="form-row">
                <div className="modal-grupo">
                  <label>Talla *</label>
                  <select
                    value={productoEditando.talla?.id_talla || productoEditando.talla?.id || ""}
                    onChange={(e) => {
                      const selectedId = parseInt(e.target.value);
                      const selectedTalla = tallas.find(t => t.id === selectedId);
                      setProductoEditando({
                        ...productoEditando,
                        talla: selectedTalla ? { id_talla: selectedTalla.id, nombre: selectedTalla.nombre } : productoEditando.talla
                      });
                    }}
                  >
                    {tallas.map((talla) => (
                      <option key={talla.id} value={talla.id}>
                        {talla.nombre} ({talla.categoria})
                      </option>
                    ))}
                  </select>
                </div>

                <div className="modal-grupo">
                  <label>Estado</label>
                  <select
                    value={productoEditando.estado}
                    onChange={(e) =>
                      setProductoEditando({
                        ...productoEditando,
                        estado: e.target.value,
                      })
                    }
                  >
                    <option value="ACTIVO">ACTIVO</option>
                    <option value="INACTIVO">INACTIVO</option>
                  </select>
                </div>
              </div>

              <div className="modal-grupo">
                <label>Descripción</label>
                <textarea
                  value={productoEditando.descripcion || ""}
                  onChange={(e) =>
                    setProductoEditando({
                      ...productoEditando,
                      descripcion: e.target.value,
                    })
                  }
                  rows="3"
                ></textarea>
              </div>

              <div className="modal-buttons">
                <button
                  type="button"
                  className="btn-modal-cancelar"
                  onClick={() => setMostrarModal(false)}
                >
                  Cancelar
                </button>
                <button 
                  type="button" 
                  className="btn-modal-guardar"
                  onClick={guardarCambios}
                >
                  💾 Guardar Cambios
                </button>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Modal Detalle (Ver detalles completos) */}
      {mostrarModalDetalle && productoDetalle && (
        <div className="modal-overlay">
          <div className="modal-detalle">
            <div className="modal-header">
              <h2>Detalles del Producto</h2>
              <button
                className="btn-cerrar-modal"
                onClick={() => setMostrarModalDetalle(false)}
              >
                ✕
              </button>
            </div>
            <div className="detalle-content">
              <div className="detalle-card-header">
                <h3>{productoDetalle.nombre}</h3>
                <span className={`badge-estado ${productoDetalle.estado.toLowerCase()}`}>
                  {productoDetalle.estado}
                </span>
              </div>
              <div className="detalle-grid">
                <div className="detalle-item">
                  <span className="detalle-label">SKU (Código):</span>
                  <span className="detalle-valor"><code>{productoDetalle.sku}</code></span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Talla:</span>
                  <span className="detalle-valor">{productoDetalle.talla?.nombre || "N/A"}</span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Precio Unitario:</span>
                  <span className="detalle-valor precio-detalle">${productoDetalle.precio_unitario.toFixed(2)}</span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Categorías:</span>
                  <div className="detalle-valor">
                    <div className="producto-categorias-tags">
                      {productoDetalle.categorias && productoDetalle.categorias.length > 0 ? (
                        productoDetalle.categorias.map((c) => (
                          <span key={c.id_categoria} className="badge-categoria-tag">
                            {c.nombre}
                          </span>
                        ))
                      ) : (
                        <span className="sin-categoria">Ninguna</span>
                      )}
                    </div>
                  </div>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Stock Actual:</span>
                  <span className={`detalle-valor ${getStockClass(productoDetalle)}`}>
                    <strong>{productoDetalle.inventario.cantidad_actual}</strong> unidades
                  </span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Límites de Inventario:</span>
                  <span className="detalle-valor">
                    Mínimo: {productoDetalle.inventario.stock_minimo} | Máximo: {productoDetalle.inventario.stock_maximo}
                  </span>
                </div>
                <div className="detalle-item full-width">
                  <span className="detalle-label">Descripción:</span>
                  <span className="detalle-valor descripcion-detalle">{productoDetalle.descripcion || "Sin descripción"}</span>
                </div>
              </div>
            </div>
            <div className="modal-buttons">
              <button
                className="btn-modal-cerrar-det"
                onClick={() => setMostrarModalDetalle(false)}
              >
                Cerrar
              </button>
            </div>
          </div>
        </div>
      )}

      {/* Estadísticas */}
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
