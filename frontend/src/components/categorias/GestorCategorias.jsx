import { useState, useEffect } from "react";
import CategoriaLista from "./CategoriaLista";
import FormularioCategoria from "./FormularioCategoria";
import { 
  obtenerCategorias, 
  crearCategoriaAPI, 
  actualizarCategoriaAPI, 
  eliminarCategoriaAPI 
} from "../../services/apiService";
import "./GestorCategorias.css";

const GestorCategorias = () => {
  const [tab, setTab] = useState("lista");
  const [categorias, setCategorias] = useState([]);
  const [loading, setLoading] = useState(true);
  const [categoriaNotif, setCategoriaNotif] = useState(null);

  // Cargar categorías al montar desde el backend
  const cargarCategorias = () => {
    setLoading(true);
    obtenerCategorias()
      .then((data) => {
        setCategorias(data);
        setLoading(false);
      })
      .catch((error) => {
        console.error("Error al inicializar categorías:", error);
        setLoading(false);
      });
  };

  useEffect(() => {
    cargarCategorias();
  }, []);

  const handleAgregarCategoria = async (nuevaCat) => {
    const guardada = await crearCategoriaAPI(nuevaCat);
    if (guardada) {
      setCategorias((prev) => [...prev, guardada]);
      
      // Mostrar notificación
      setCategoriaNotif({ tipo: "crear", nombre: guardada.nombre });
      setTimeout(() => setCategoriaNotif(null), 3000);
      
      // Cambiar a la pestaña de lista
      setTab("lista");
    } else {
      alert("No se pudo guardar la categoría en el servidor.");
    }
  };

  const handleEditarCategoria = async (catEditada) => {
    const completado = await actualizarCategoriaAPI(catEditada.id_categoria, catEditada);
    if (completado) {
      setCategorias((prev) =>
        prev.map((c) => (c.id_categoria === catEditada.id_categoria ? catEditada : c))
      );

      // Mostrar notificación
      setCategoriaNotif({ tipo: "editar", nombre: catEditada.nombre });
      setTimeout(() => setCategoriaNotif(null), 3000);
    } else {
      alert("No se pudo actualizar la categoría en el servidor.");
    }
  };

  const handleEliminarCategoria = async (id) => {
    const completado = await eliminarCategoriaAPI(id);
    if (completado) {
      setCategorias((prev) => prev.filter((c) => c.id_categoria !== id));

      // Mostrar notificación
      setCategoriaNotif({ tipo: "eliminar" });
      setTimeout(() => setCategoriaNotif(null), 3000);
    } else {
      alert("No se pudo eliminar la categoría. Verifique si tiene subcategorías o productos asociados.");
    }
  };

  return (
    <div className="gestor-categorias-container">
      {/* Notificación flotante */}
      {categoriaNotif && (
        <div className={`notificacion-flotante ${categoriaNotif.tipo === "eliminar" ? "eliminar" : ""}`}>
          <div className="notif-content">
            <span className="notif-icon">
              {categoriaNotif.tipo === "eliminar" ? "🗑️" : "✅"}
            </span>
            <div>
              <strong>
                {categoriaNotif.tipo === "crear" && "¡Categoría agregada!"}
                {categoriaNotif.tipo === "editar" && "¡Categoría actualizada!"}
                {categoriaNotif.tipo === "eliminar" && "¡Categoría eliminada!"}
              </strong>
              {categoriaNotif.nombre && <p>{categoriaNotif.nombre}</p>}
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
          <span className="tab-icon">🏷️</span>
          Listado de Categorías
        </button>
        <button
          className={`tab-btn ${tab === "agregar" ? "activo" : ""}`}
          onClick={() => setTab("agregar")}
        >
          <span className="tab-icon">➕</span>
          Agregar Categoría
        </button>
      </div>

      {/* Contenido de tabs */}
      <div className="tabs-contenido">
        {tab === "lista" && (
          <CategoriaLista
            categorias={categorias}
            loading={loading}
            onEditar={handleEditarCategoria}
            onEliminar={handleEliminarCategoria}
          />
        )}
        {tab === "agregar" && (
          <FormularioCategoria
            categoriasExistentes={categorias}
            onAgregar={handleAgregarCategoria}
          />
        )}
      </div>
    </div>
  );
};

export default GestorCategorias;
