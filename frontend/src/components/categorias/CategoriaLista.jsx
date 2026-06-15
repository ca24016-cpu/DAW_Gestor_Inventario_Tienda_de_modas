import { useState } from "react";
import "./CategoriaLista.css";

const CategoriaLista = ({ categorias, loading, onEditar, onEliminar }) => {
  const [filtro, setFiltro] = useState("TODOS");
  const [busqueda, setBusqueda] = useState("");
  const [categoriaEditando, setCategoriaEditando] = useState(null);
  const [mostrarModal, setMostrarModal] = useState(false);

  // Filtrado y búsqueda
  const categoriasFiltradas = categorias.filter((cat) => {
    const cumpleFiltro =
      filtro === "TODOS"
        ? true
        : filtro === "ACTIVO"
        ? cat.activo === true
        : cat.activo === false;

    const cumpleBusqueda =
      cat.nombre.toLowerCase().includes(busqueda.toLowerCase()) ||
      (cat.descripcion &&
        cat.descripcion.toLowerCase().includes(busqueda.toLowerCase()));

    return cumpleFiltro && cumpleBusqueda;
  });

  // Resolver nombre de categoría padre
  const obtenerNombrePadre = (idPadre) => {
    if (!idPadre) return "Ninguna (Principal)";
    const padre = categorias.find((c) => c.id_categoria === parseInt(idPadre));
    return padre ? padre.nombre : `ID: ${idPadre}`;
  };

  const handleEditar = (cat) => {
    setCategoriaEditando({ ...cat });
    setMostrarModal(true);
  };

  const handleGuardar = (e) => {
    e.preventDefault();
    if (!categoriaEditando.nombre.trim()) {
      alert("El nombre de la categoría es obligatorio");
      return;
    }
    onEditar(categoriaEditando);
    setMostrarModal(false);
  };

  const handleEliminar = (id, nombre) => {
    const confirmar = window.confirm(
      `¿Está seguro de que desea eliminar la categoría "${nombre}"?`
    );
    if (confirmar) {
      onEliminar(id);
    }
  };

  if (loading) {
    return (
      <div className="categoria-lista-container">
        <div className="skeleton-table">
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
          <div className="skeleton-row"></div>
        </div>
      </div>
    );
  }

  return (
    <div className="categoria-lista-container">
      {/* Controles de Filtro y Búsqueda */}
      <div className="lista-header">
        <div className="busqueda-wrapper">
          <input
            type="text"
            placeholder="🔍 Buscar categorías..."
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
            Todas ({categorias.length})
          </button>
          <button
            className={`btn-filtro ${filtro === "ACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("ACTIVO")}
          >
            Activas ({categorias.filter((c) => c.activo).length})
          </button>
          <button
            className={`btn-filtro ${filtro === "INACTIVO" ? "activo" : ""}`}
            onClick={() => setFiltro("INACTIVO")}
          >
            Inactivas ({categorias.filter((c) => !c.activo).length})
          </button>
        </div>
      </div>

      {/* Tabla */}
      {categoriasFiltradas.length === 0 ? (
        <div className="sin-datos">
          <p>No se encontraron categorías que coincidan con la búsqueda.</p>
        </div>
      ) : (
        <div className="tabla-responsiva">
          <table className="tabla-categorias">
            <thead>
              <tr>
                <th>ID</th>
                <th>Nombre</th>
                <th>Descripción</th>
                <th>Categoría Padre</th>
                <th>Estado</th>
                <th className="th-acciones">Acciones</th>
              </tr>
            </thead>
            <tbody>
              {categoriasFiltradas.map((cat) => (
                <tr key={cat.id_categoria} className="tabla-fila">
                  <td data-label="ID" className="id-col">
                    <code>#{cat.id_categoria}</code>
                  </td>
                  <td data-label="Nombre">
                    <strong className="categoria-nombre">{cat.nombre}</strong>
                  </td>
                  <td data-label="Descripción">
                    <span className="categoria-desc">
                      {cat.descripcion || "Sin descripción"}
                    </span>
                  </td>
                  <td data-label="Categoría Padre">
                    <span className="badge-padre">
                      {obtenerNombrePadre(cat.id_categoria_padre)}
                    </span>
                  </td>
                  <td data-label="Estado">
                    <span
                      className={`badge-estado ${
                        cat.activo ? "activo" : "inactivo"
                      }`}
                    >
                      {cat.activo ? "ACTIVO" : "INACTIVO"}
                    </span>
                  </td>
                  <td data-label="Acciones" className="acciones">
                    <button
                      className="btn-editar"
                      title="Editar"
                      onClick={() => handleEditar(cat)}
                    >
                      ✏️
                    </button>
                    <button
                      className="btn-eliminar"
                      title="Eliminar"
                      onClick={() =>
                        handleEliminar(cat.id_categoria, cat.nombre)
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

      {/* Modal de Edición */}
      {mostrarModal && categoriaEditando && (
        <div className="modal-overlay">
          <div className="modal-editar">
            <div className="modal-header">
              <h2>Editar Categoría</h2>
              <button
                className="btn-cerrar-modal"
                onClick={() => setMostrarModal(false)}
              >
                ✕
              </button>
            </div>
            <form onSubmit={handleGuardar} className="modal-form">
              <div className="modal-grupo">
                <label>Nombre de Categoría *</label>
                <input
                  type="text"
                  value={categoriaEditando.nombre}
                  onChange={(e) =>
                    setCategoriaEditando({
                      ...categoriaEditando,
                      nombre: e.target.value,
                    })
                  }
                  required
                />
              </div>

              <div className="modal-grupo">
                <label>Descripción</label>
                <textarea
                  value={categoriaEditando.descripcion || ""}
                  onChange={(e) =>
                    setCategoriaEditando({
                      ...categoriaEditando,
                      descripcion: e.target.value,
                    })
                  }
                  rows="3"
                ></textarea>
              </div>

              <div className="modal-grupo">
                <label>Categoría Padre</label>
                <select
                  value={categoriaEditando.id_categoria_padre || ""}
                  onChange={(e) =>
                    setCategoriaEditando({
                      ...categoriaEditando,
                      id_categoria_padre: e.target.value
                        ? parseInt(e.target.value)
                        : null,
                    })
                  }
                >
                  <option value="">Ninguna (Principal)</option>
                  {categorias
                    .filter((c) => c.id_categoria !== categoriaEditando.id_categoria) // No ser padre de sí misma
                    .map((c) => (
                      <option key={c.id_categoria} value={c.id_categoria}>
                        {c.nombre}
                      </option>
                    ))}
                </select>
              </div>

              <div className="modal-grupo checkbox-grupo">
                <label className="switch-label">
                  <input
                    type="checkbox"
                    checked={categoriaEditando.activo}
                    onChange={(e) =>
                      setCategoriaEditando({
                        ...categoriaEditando,
                        activo: e.target.checked,
                      })
                    }
                  />
                  <span>Categoría Activa</span>
                </label>
              </div>

              <div className="modal-buttons">
                <button
                  type="button"
                  className="btn-modal-cancelar"
                  onClick={() => setMostrarModal(false)}
                >
                  Cancelar
                </button>
                <button type="submit" className="btn-modal-guardar">
                  💾 Guardar
                </button>
              </div>
            </form>
          </div>
        </div>
      )}

      {/* Estadísticas */}
      <div className="estadisticas-lista">
        <div className="stat">
          <span className="stat-label">Total de Categorías:</span>
          <span className="stat-valor">{categorias.length}</span>
        </div>
        <div className="stat">
          <span className="stat-label">Categorías Activas:</span>
          <span className="stat-valor">
            {categorias.filter((c) => c.activo).length}
          </span>
        </div>
        <div className="stat">
          <span className="stat-label">Categorías Principales:</span>
          <span className="stat-valor">
            {categorias.filter((c) => !c.id_categoria_padre).length}
          </span>
        </div>
      </div>
    </div>
  );
};

export default CategoriaLista;
