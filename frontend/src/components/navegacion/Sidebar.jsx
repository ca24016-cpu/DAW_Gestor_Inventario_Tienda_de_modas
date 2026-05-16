import { useState } from "react";
import "./Sidebar.css";

const Sidebar = ({ seccionActiva, onCambioSeccion }) => {
  const [menuAbierto, setMenuAbierto] = useState(true);

  const opciones = [
    { id: "dashboard", label: "Dashboard", subtitulo: "Inicio", icon: "🏠" },
    { id: "inventario", label: "Inventario", subtitulo: "Gestión", icon: "📦" },
    { id: "proveedores", label: "Proveedores", subtitulo: "Directorio", icon: "🤝" },
    { id: "categorias", label: "Categorías", subtitulo: "Organización", icon: "🏷️" },
    { id: "reportes", label: "Reportes", subtitulo: "E/S", icon: "📊" },
  ];

  const handleClick = (id) => {
    onCambioSeccion(id);
  };

  return (
    <>
      {/* Botón para abrir/cerrar menú en móvil */}
      <button
        className="btn-menu-toggle"
        onClick={() => setMenuAbierto(!menuAbierto)}
        title={menuAbierto ? "Cerrar menú" : "Abrir menú"}
      >
        ☰
      </button>

      {/* Overlay para cerrar menú en móvil */}
      {menuAbierto && (
        <div
          className="sidebar-overlay"
          onClick={() => setMenuAbierto(false)}
        ></div>
      )}

      {/* Menú lateral */}
      <aside
        className={`menu-lateral ${menuAbierto ? "abierto" : "cerrado"}`}
        id="sidebarMenu"
      >
        <div className="sidebar-brand">
          <span className="brand-icon">🏪</span>
          <span className="brand-text">FashionTrack</span>
        </div>

        <nav className="navegacion-principal">
          <ul>
            {opciones.map((opcion) => (
              <li key={opcion.id}>
                <button
                  className={`item-menu ${
                    seccionActiva === opcion.id ? "activo" : ""
                  }`}
                  onClick={() => {
                    handleClick(opcion.id);
                    setMenuAbierto(false);
                  }}
                >
                  <span className="menu-icon">{opcion.icon}</span>
                  <div className="menu-texto">
                    <span className="menu-label">{opcion.label}</span>
                    <span className="menu-subtitulo">{opcion.subtitulo}</span>
                  </div>
                </button>
              </li>
            ))}
          </ul>
        </nav>

        <div className="sidebar-footer">
          <p>© 2026 FashionTrack</p>
        </div>
      </aside>
    </>
  );
};

export default Sidebar;
