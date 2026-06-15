import { useState, useEffect } from "react";
import ProveedorLista from "./ProveedorLista";
import FormularioProveedor from "./FormularioProveedor";
import { 
  obtenerProveedores, 
  crearProveedorAPI, 
  actualizarProveedorAPI, 
  eliminarProveedorAPI 
} from "../../services/apiService";
import "./GestorProveedores.css";

const GestorProveedores = () => {
  const [tab, setTab] = useState("lista");
  const [proveedores, setProveedores] = useState([]);
  const [loading, setLoading] = useState(true);
  const [proveedorNotif, setProveedorNotif] = useState(null);

  // Cargar proveedores al montar
  const cargarProveedores = () => {
    setLoading(true);
    obtenerProveedores()
      .then((data) => {
        setProveedores(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error al inicializar proveedores:", error);
        setLoading(false);
      });
  };

  useEffect(() => {
    cargarProveedores();
  }, []);

  const handleAgregarProveedor = async (nuevoProv) => {
    const guardado = await crearProveedorAPI(nuevoProv);
    if (guardado) {
      setProveedores((prev) => [...prev, guardado]);

      // Mostrar notificación
      setProveedorNotif({ tipo: "crear", nombre: guardado.nombre });
      setTimeout(() => setProveedorNotif(null), 3000);

      // Cambiar a la pestaña de lista
      setTab("lista");
    } else {
      alert("No se pudo registrar al proveedor en el servidor.");
    }
  };

  const handleEditarProveedor = async (provEditado) => {
    const completado = await actualizarProveedorAPI(provEditado.id_proveedor, provEditado);
    if (completado) {
      setProveedores((prev) =>
        prev.map((p) => (p.id_proveedor === provEditado.id_proveedor ? provEditado : p))
      );

      // Mostrar notificación
      setProveedorNotif({ tipo: "editar", nombre: provEditado.nombre });
      setTimeout(() => setProveedorNotif(null), 3000);
    } else {
      alert("No se pudo actualizar los datos del proveedor en el servidor.");
    }
  };

  const handleEliminarProveedor = async (id) => {
    const completado = await eliminarProveedorAPI(id);
    if (completado) {
      setProveedores((prev) => prev.filter((p) => p.id_proveedor !== id));

      // Mostrar notificación
      setProveedorNotif({ tipo: "eliminar" });
      setTimeout(() => setProveedorNotif(null), 3000);
    } else {
      alert("No se pudo eliminar al proveedor. Verifique si tiene productos asociados.");
    }
  };

  return (
    <div className="gestor-proveedores-container">
      {/* Notificación flotante */}
      {proveedorNotif && (
        <div className={`notificacion-flotante ${proveedorNotif.tipo === "eliminar" ? "eliminar" : ""}`}>
          <div className="notif-content">
            <span className="notif-icon">
              {proveedorNotif.tipo === "eliminar" ? "🗑️" : "✅"}
            </span>
            <div>
              <strong>
                {proveedorNotif.tipo === "crear" && "¡Proveedor registrado!"}
                {proveedorNotif.tipo === "editar" && "¡Proveedor actualizado!"}
                {proveedorNotif.tipo === "eliminar" && "¡Proveedor eliminado!"}
              </strong>
              {proveedorNotif.nombre && <p>{proveedorNotif.nombre}</p>}
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
          <span className="tab-icon">🤝</span>
          Directorio de Proveedores
        </button>
        <button
          className={`tab-btn ${tab === "agregar" ? "activo" : ""}`}
          onClick={() => setTab("agregar")}
        >
          <span className="tab-icon">➕</span>
          Registrar Proveedor
        </button>
      </div>

      {/* Contenido de tabs */}
      <div className="tabs-contenido">
        {tab === "lista" && (
          <ProveedorLista
            proveedores={proveedores}
            loading={loading}
            onEditar={handleEditarProveedor}
            onEliminar={handleEliminarProveedor}
          />
        )}
        {tab === "agregar" && (
          <FormularioProveedor onAgregar={handleAgregarProveedor} />
        )}
      </div>
    </div>
  );
};

export default GestorProveedores;
