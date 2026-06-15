import { useState } from "react";
import Sidebar from "./components/navegacion/Sidebar";
import Dashboard from "./components/dashboard/Dashboard";
import GestorProductos from "./components/productos/GestorProductos";
import GestorProveedores from "./components/proveedores/GestorProveedores";
import GestorCategorias from "./components/categorias/GestorCategorias";
import Reportes from "./components/Reportes";
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
        return <GestorProveedores />;
      case "categorias":
        return <GestorCategorias />;
      case "reportes":
        return <Reportes />;
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
