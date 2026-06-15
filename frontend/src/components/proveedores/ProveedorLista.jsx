import { useState } from "react";
import "./ProveedorLista.css";

const ProveedorLista = ({ proveedores, loading, onEditar, onEliminar }) => {
  const [filtro, setFiltro] = useState("TODOS");
  const [busqueda, setBusqueda] = useState("");
  
  const [proveedorEditando, setProveedorEditando] = useState(null);
  const [mostrarModalEditar, setMostrarModalEditar] = useState(false);
  
  const [proveedorDetalle, setProveedorDetalle] = useState(null);
  const [mostrarModalDetalle, setMostrarModalDetalle] = useState(false);

  // Filtrado y búsqueda
  const proveedoresFiltrados = proveedores.filter((prov) => {
    const cumpleFiltro =
      filtro === "TODOS"
        ? true
        : filtro === "ACTIVO"
        ? prov.activo === true
        : prov.activo === false;

    const cumpleBusqueda =
      prov.nombre.toLowerCase().includes(busqueda.toLowerCase()) ||
      (prov.contacto && prov.contacto.toLowerCase().includes(busqueda.toLowerCase())) ||
      (prov.telefono && prov.telefono.includes(busqueda));

    return cumpleFiltro && cumpleBusqueda;
  });

  const handleEditar = (prov) => {
    setProveedorEditando({
      ...prov,
      email: prov.email || "",
      direccion: prov.direccion || "",
    });
    setMostrarModalEditar(true);
  };

  const handleVerDetalles = (prov) => {
    setProveedorDetalle(prov);
    setMostrarModalDetalle(true);
  };

  const handleGuardar = (e) => {
    e.preventDefault();
    if (!proveedorEditando.nombre.trim()) {
      alert("El nombre de la empresa es obligatorio");
      return;
    }
    onEditar(proveedorEditando);
    setMostrarModalEditar(false);
  };

  const handleEliminar = (id, nombre) => {
    const confirmar = window.confirm(
      `¿Está seguro de que desea eliminar al proveedor "${nombre}"?`
    );
    if (confirmar) {
      onEliminar(id);
    }
  };

  if (loading) {
    return (
      <div className="proveedor-lista-container">
        <div className="skeleton-table">
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
        </div>
      </div>
    );
  }

  return (
    <div className="proveedor-lista-container">
      {/* Controles de búsqueda y filtros */}
      <div className="lista-header">
        <div className="busqueda-wrapper">
          <input
            type="text"
            placeholder="🔍 Buscar proveedores por nombre, contacto o tel..."
            value={busqueda}
            onChange={(e) => setBusqueda(e.target.value)}
            className="input-busqueda"
          />
        </div>
        <div className="filtro-grupo">
          <button
            className={`btn-filtro ${filtro === "TODOS" ? "activo" : ""}`}
            onClick={() => setFiltro("TODOS")}
          >
            Todos ({proveedores.length})
          </button>
          <button
            className={`btn-filtro ${filtro === "ACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("ACTIVO")}
          >
            Activos ({proveedores.filter((p) => p.activo).length})
          </button>
          <button
            className={`btn-filtro ${filtro === "INACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("INACTIVO")}
          >
            Inactivos ({proveedores.filter((p) => !p.activo).length})
          </button>
        </div>
      </div>

      {/* Tabla */}
      {proveedoresFiltrados.length === 0 ? (
        <div className="sin-datos">
          <p>No se encontraron proveedores que coincidan con la búsqueda.</p>
        </div>
      ) : (
        <div className="tabla-responsiva">
          <table className="tabla-proveedores">
            <thead>
              <tr>
                <th>ID</th>
                <th>Proveedor</th>
                <th>Contacto</th>
                <th>Teléfono</th>
                <th>Estado</th>
                <th className="th-acciones">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {proveedoresFiltrados.map((prov) => (
                <tr key={prov.id_proveedor} className="tabla-fila">
                  <td data-label="ID" className="id-col">
                    <code>#{prov.id_proveedor}</code>
                  </td>
                  <td data-label="Proveedor">
                    <strong className="proveedor-nombre">{prov.nombre}</strong>
                  </td>
                  <td data-label="Contacto">
                    <span className="proveedor-contacto-nombre">
                      {prov.contacto || "N/A"}
                    </span>
                  </td>
                  <td data-label="Teléfono" className="tel-col">
                    <span>{prov.telefono || "N/A"}</span>
                  </td>
                  <td data-label="Estado">
                    <span
                      className={`badge-estado ${
                        prov.activo ? "activo" : "inactivo"
                      }`}
                    >
                      {prov.activo ? "ACTIVO" : "INACTIVO"}
                    </span>
                  </td>
                  <td data-label="Acciones" className="acciones">
                    <button
                      className="btn-ver"
                      title="Ver Detalles"
                      onClick={() => handleVerDetalles(prov)}
                    >
                      👁️
                    </button>
                    <button
                      className="btn-editar"
                      title="Editar"
                      onClick={() => handleEditar(prov)}
                    >
                      ✏️
                    </button>
                    <button
                      className="btn-eliminar"
                      title="Eliminar"
                      onClick={() =>
                        handleEliminar(prov.id_proveedor, prov.nombre)
                      }
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

      {/* Modal Detalle (Ver detalles completos) */}
      {mostrarModalDetalle && proveedorDetalle && (
        <div className="modal-overlay">
          <div className="modal-detalle">
            <div className="modal-header">
              <h2>Detalles del Proveedor</h2>
              <button
                className="btn-cerrar-modal"
                onClick={() => setMostrarModalDetalle(false)}
              >
                ✕
              </button>
            </div>
            <div className="detalle-content">
              <div className="detalle-card-header">
                <h3>{proveedorDetalle.nombre}</h3>
                <span
                  className={`badge-estado ${
                    proveedorDetalle.activo ? "activo" : "inactivo"
                  }`}
                >
                  {proveedorDetalle.activo ? "Activo" : "Inactivo"}
                </span>
              </div>
              <div className="detalle-grid">
                <div className="detalle-item">
                  <span className="detalle-label">Contacto:</span>
                  <span className="detalle-valor">{proveedorDetalle.contacto || "No registrado"}</span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Teléfono:</span>
                  <span className="detalle-valor">{proveedorDetalle.telefono || "No registrado"}</span>
                </div>
                <div className="detalle-item">
                  <span className="detalle-label">Email:</span>
                  <span className="detalle-valor">{proveedorDetalle.email || "No registrado"}</span>
                </div>
                <div className="detalle-item full-width">
                  <span className="detalle-label">Dirección:</span>
                  <span className="detalle-valor">{proveedorDetalle.direccion || "No registrada"}</span>
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

      {/* Modal Editar */}
      {mostrarModalEditar && proveedorEditando && (
        <div className="modal-overlay">
          <div className="modal-editar">
            <div className="modal-header">
              <h2>Editar Proveedor</h2>
              <button
                className="btn-cerrar-modal"
                onClick={() => setMostrarModalEditar(false)}
              >
                ✕
              </button>
            </div>
            <form onSubmit={handleGuardar} className="modal-form">
              <div className="modal-grupo">
                <label>Nombre de la Empresa *</label>
                <input
                  type="text"
                  value={proveedorEditando.nombre}
                  onChange={(e) =>
                    setProveedorEditando({
                      ...proveedorEditando,
                      nombre: e.target.value,
                    })
                  }
                  required
                />
              </div>

              <div className="modal-grupo">
                <label>Persona de Contacto</label>
                <input
                  type="text"
                  value={proveedorEditando.contacto || ""}
                  onChange={(e) =>
                    setProveedorEditando({
                      ...proveedorEditando,
                      contacto: e.target.value,
                    })
                  }
                />
              </div>

              <div className="form-row">
                <div className="modal-grupo">
                  <label>Teléfono</label>
                  <input
                    type="text"
                    value={proveedorEditando.telefono || ""}
                    onChange={(e) =>
                      setProveedorEditando({
                        ...proveedorEditando,
                        telefono: e.target.value,
                      })
                    }
                  />
                </div>

                <div className="modal-grupo">
                  <label>Email</label>
                  <input
                    type="email"
                    value={proveedorEditando.email || ""}
                    onChange={(e) =>
                      setProveedorEditando({
                        ...proveedorEditando,
                        email: e.target.value,
                      })
                    }
                  />
                </div>
              </div>

              <div className="modal-grupo">
                <label>Dirección</label>
                <textarea
                  value={proveedorEditando.direccion || ""}
                  onChange={(e) =>
                    setProveedorEditando({
                      ...proveedorEditando,
                      direccion: e.target.value,
                    })
                  }
                  rows="2"
                ></textarea>
              </div>

              <div className="modal-grupo checkbox-grupo">
                <label className="switch-label">
                  <input
                    type="checkbox"
                    checked={proveedorEditando.activo}
                    onChange={(e) =>
                      setProveedorEditando({
                        ...proveedorEditando,
                        activo: e.target.checked,
                      })
                    }
                  />
                  <span>Proveedor Activo</span>
                </label>
              </div>

              <div className="modal-buttons">
                <button
                  type="button"
                  className="btn-modal-cancelar"
                  onClick={() => setMostrarModalEditar(false)}
                >
                  Cancelar
                </button>
                <button type="submit" className="btn-modal-guardar">
                  💾 Guardar Cambios
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Panel de Estadísticas */}
      <div className="estadisticas-lista">
        <div className="stat">
          <span className="stat-label">Total de Proveedores:</span>
          <span className="stat-valor">{proveedores.length}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Proveedores Activos:</span>
          <span className="stat-valor">
            {proveedores.filter((p) => p.activo).length}
          </span>
        </div>
        <div className="stat">
          <span className="stat-label">Proveedores Inactivos:</span>
          <span className="stat-valor stat-alerta">
            {proveedores.filter((p) => !p.activo).length}
          </span>
        </div>
      </div>
    </div>
  );
};

export default ProveedorLista;
