import { useState } from "react";
import Sidebar from "./components/navegacion/Sidebar";
import Dashboard from "./components/dashboard/Dashboard";
import GestorProductos from "./components/productos/GestorProductos";
import "./AppLayout.css";

const AppLayout = () => {
  const [seccionActiva, setSeccionActiva] = useState("dashboard");

  const renderizarSeccion = () => {
    switch (seccionActiva) {
      case "dashboard":
        return <Dashboard />;
      case "inventario":
        return <GestorProductos />;
      case "proveedores":
        return (
          <div className="seccion-contenedor">
            <h2>Gestión de Proveedores</h2>
            <p>Esta sección está en desarrollo...</p>
          </div>
        );
      case "categorias":
        return (
          <div className="seccion-contenedor">
            <h2>Gestión de Categorías</h2>
            <p>Esta sección está en desarrollo...</p>
          </div>
        );
      case "reportes":
        return (
          <div className="seccion-contenedor">
            <h2>Reportes</h2>
            <p>Esta sección está en desarrollo...</p>
          </div>
        );
      default:
        return <Dashboard />;
    }
  };

  return (
    <div className="app-layout">
      <Sidebar
        seccionActiva={seccionActiva}
        onCambioSeccion={setSeccionActiva}
      />
      <main className="contenido-principal">
        {renderizarSeccion()}
      </main>
    </div>
  );
};

export default AppLayout;
