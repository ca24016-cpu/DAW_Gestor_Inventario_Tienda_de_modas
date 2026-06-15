import { useState } from "react";
import "./FormularioCategoria.css";

const FormularioCategoria = ({ categoriasExistentes, onAgregar }) => {
  const [formData, setFormData] = useState({
    nombre: "Prendas de Invierno",
    descripcion: "Ropa abrigadora como chaquetas, abrigos y bufandas.",
    id_categoria_padre: "",
    activo: true,
  });

  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);

  const validarFormulario = () => {
    const nuevosErrores = {};
    if (!formData.nombre.trim()) {
      nuevosErrores.nombre = "El nombre de la categoría es obligatorio";
    } else if (
      categoriasExistentes.some(
        (c) => c.nombre.toLowerCase().trim() === formData.nombre.toLowerCase().trim()
      )
    ) {
      nuevosErrores.nombre = "Ya existe una categoría con este nombre";
    }
    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validarFormulario()) return;

    setCargando(true);
    try {
      // Simular delay de red
      await new Promise((resolve) => setTimeout(resolve, 300));

      const nuevaCategoria = {
        id_categoria: Math.floor(Math.random() * 900) + 100, // Generar ID aleatorio
        nombre: formData.nombre.trim(),
        descripcion: formData.descripcion.trim(),
        id_categoria_padre: formData.id_categoria_padre
          ? parseInt(formData.id_categoria_padre)
          : null,
        activo: formData.activo,
      };

      onAgregar(nuevaCategoria);

      // Resetear formulario con valores vacíos o default
      setFormData({
        nombre: "",
        descripcion: "",
        id_categoria_padre: "",
        activo: true,
      });
      setErrores({});
    } catch (error) {
      console.error("Error al registrar categoría:", error);
    } finally {
      setCargando(false);
    }
  };

  const handleLimpiar = () => {
    setFormData({
      nombre: "",
      descripcion: "",
      id_categoria_padre: "",
      activo: true,
    });
    setErrores({});
  };

  const obtenerNombrePadre = (idPadre) => {
    if (!idPadre) return null;
    const padre = categoriasExistentes.find((c) => c.id_categoria === parseInt(idPadre));
    return padre ? padre.nombre : null;
  };

  return (
    <div className="formulario-categoria-container">
      <div className="formulario-header">
        <h2>Agregar Nueva Categoría</h2>
        <p>Registre una nueva categoría para clasificar sus prendas y calzados</p>
      </div>

      <form className="categoria-form" onSubmit={handleSubmit}>
        <fieldset className="form-fieldset">
          <legend>Información General</legend>

          <div className="form-grupo">
            <label htmlFor="nombre">Nombre de Categoría *</label>
            <input
              type="text"
              id="nombre"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              placeholder="Ej: Calzado Infantil, Abrigos, Accesorios"
              className={errores.nombre ? "error" : ""}
            />
            {errores.nombre && <span className="error-msg">{errores.nombre}</span>}
          </div>

          <div className="form-grupo">
            <label htmlFor="descripcion">Descripción</label>
            <textarea
              id="descripcion"
              name="descripcion"
              value={formData.descripcion}
              onChange={handleChange}
              placeholder="Describa brevemente qué tipo de prendas incluye esta categoría..."
              rows="3"
            ></textarea>
          </div>
        </fieldset>

        <fieldset className="form-fieldset">
          <legend>Jerarquía y Estado</legend>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="id_categoria_padre">Categoría Padre (Subcategoría de...)</label>
              <select
                id="id_categoria_padre"
                name="id_categoria_padre"
                value={formData.id_categoria_padre}
                onChange={handleChange}
              >
                <option value="">Ninguna (Es una categoría principal)</option>
                {categoriasExistentes
                  .filter((c) => c.activo)
                  .map((c) => (
                    <option key={c.id_categoria} value={c.id_categoria}>
                      {c.nombre}
                    </option>
                  ))}
              </select>
            </div>

            <div className="form-grupo toggle-grupo">
              <label className="switch-label">
                <input
                  type="checkbox"
                  id="activo"
                  name="activo"
                  checked={formData.activo}
                  onChange={handleChange}
                />
                <span>Habilitada inmediatamente</span>
              </label>
            </div>
          </div>
        </fieldset>

        {/* Botones de acción */}
        <div className="form-acciones">
          <button type="submit" className="btn-guardar" disabled={cargando}>
            {cargando ? "Guardando..." : "💾 Guardar Categoría"}
          </button>
          <button type="button" className="btn-limpiar" onClick={handleLimpiar}>
            🔄 Limpiar Formulario
          </button>
        </div>
      </form>

      {/* Sección Vista Previa en Tiempo Real */}
      <div className="preview-section">
        <h3>Vista Previa de Categoría</h3>
        <div className="preview-card">
          <div className="preview-header">
            <h4>{formData.nombre || "Nueva Categoría"}</h4>
            <span className={`badge-estado ${formData.activo ? "activo" : "inactivo"}`}>
              {formData.activo ? "Activa" : "Inactiva"}
            </span>
          </div>
          <p className="preview-desc">
            {formData.descripcion || "Agregue una descripción para previsualizar."}
          </p>
          <div className="preview-footer">
            <div className="preview-info">
              <span className="label">Clasificación:</span>
              <span className="valor">
                {formData.id_categoria_padre ? (
                  <>
                    Subcategoría de <strong>{obtenerNombrePadre(formData.id_categoria_padre)}</strong>
                  </>
                ) : (
                  <strong>Categoría Principal</strong>
                )}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FormularioCategoria;
