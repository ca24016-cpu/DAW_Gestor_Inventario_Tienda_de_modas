import { useState } from "react";
import "./FormularioProveedor.css";

const FormularioProveedor = ({ onAgregar }) => {
  const [formData, setFormData] = useState({
    nombre: "Prendas de Algodón S.A. de C.V.",
    contacto: "Roberto Menéndez",
    telefono: "7777-5555",
    email: "roberto@prendasalgodon.com",
    direccion: "Zona Industrial Plan de la Laguna, Calle L-3, Antiguo Cuscatlán.",
    activo: true,
  });

  const [errores, setErrores] = useState({});
  const [cargando, setCargando] = useState(false);

  const validarFormulario = () => {
    const nuevosErrores = {};
    if (!formData.nombre.trim()) {
      nuevosErrores.nombre = "El nombre de la empresa es obligatorio";
    }

    if (formData.email && !/\S+@\S+\.\S+/.test(formData.email)) {
      nuevosErrores.email = "El correo electrónico no tiene un formato válido";
    }

    if (formData.telefono && !/^\+?\d{2,4}[- ]?\d{4,8}$/.test(formData.telefono.replace(/\s+/g, ""))) {
      // Validación básica para números telefónicos (incluyendo guiones y espacios)
      nuevosErrores.telefono = "El teléfono no es válido (ej: 7777-1111)";
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

      const nuevoProveedor = {
        id_proveedor: Math.floor(Math.random() * 900) + 100, // ID aleatorio
        nombre: formData.nombre.trim(),
        contacto: formData.contacto.trim(),
        telefono: formData.telefono.trim(),
        email: formData.email.trim(),
        direccion: formData.direccion.trim(),
        activo: formData.activo,
      };

      onAgregar(nuevoProveedor);

      // Limpiar formulario
      setFormData({
        nombre: "",
        contacto: "",
        telefono: "",
        email: "",
        direccion: "",
        activo: true,
      });
      setErrores({});
    } catch (error) {
      console.error("Error al registrar proveedor:", error);
    } finally {
      setCargando(false);
    }
  };

  const handleLimpiar = () => {
    setFormData({
      nombre: "",
      contacto: "",
      telefono: "",
      email: "",
      direccion: "",
      activo: true,
    });
    setErrores({});
  };

  return (
    <div className="formulario-proveedor-container">
      <div className="formulario-header">
        <h2>Registrar Nuevo Proveedor</h2>
        <p>Complete los datos para dar de alta un nuevo proveedor en el directorio de la tienda</p>
      </div>

      <form className="proveedor-form" onSubmit={handleSubmit}>
        <fieldset className="form-fieldset">
          <legend>Información Corporativa</legend>

          <div className="form-grupo">
            <label htmlFor="nombre">Nombre de la Empresa (Razón Social) *</label>
            <input
              type="text"
              id="nombre"
              name="nombre"
              value={formData.nombre}
              onChange={handleChange}
              placeholder="Ej: Textiles Globales S.A."
              className={errores.nombre ? "error" : ""}
            />
            {errores.nombre && <span className="error-msg">{errores.nombre}</span>}
          </div>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="contacto">Persona de Contacto</label>
              <input
                type="text"
                id="contacto"
                name="contacto"
                value={formData.contacto}
                onChange={handleChange}
                placeholder="Ej: Carlos Ruiz (Gerente de Ventas)"
              />
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
                <span>Habilitar inmediatamente</span>
              </label>
            </div>
          </div>
        </fieldset>

        <fieldset className="form-fieldset">
          <legend>Datos de Contacto y Ubicación</legend>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="telefono">Teléfono de Contacto</label>
              <input
                type="text"
                id="telefono"
                name="telefono"
                value={formData.telefono}
                onChange={handleChange}
                placeholder="Ej: 7777-1111"
                className={errores.telefono ? "error" : ""}
              />
              {errores.telefono && <span className="error-msg">{errores.telefono}</span>}
            </div>

            <div className="form-grupo">
              <label htmlFor="email">Correo Electrónico</label>
              <input
                type="email"
                id="email"
                name="email"
                value={formData.email}
                onChange={handleChange}
                placeholder="Ej: ventas@proveedor.com"
                className={errores.email ? "error" : ""}
              />
              {errores.email && <span className="error-msg">{errores.email}</span>}
            </div>
          </div>

          <div className="form-grupo">
            <label htmlFor="direccion">Dirección Física / Almacén</label>
            <textarea
              id="direccion"
              name="direccion"
              value={formData.direccion}
              onChange={handleChange}
              placeholder="Calle, Número, Ciudad, País..."
              rows="3"
            ></textarea>
          </div>
        </fieldset>

        {/* Botones de acción */}
        <div className="form-acciones">
          <button type="submit" className="btn-guardar" disabled={cargando}>
            {cargando ? "Guardando..." : "💾 Guardar Proveedor"}
          </button>
          <button type="button" className="btn-limpiar" onClick={handleLimpiar}>
            🔄 Limpiar Formulario
          </button>
        </div>
      </form>

      {/* Sección Vista Previa en Tiempo Real */}
      <div className="preview-section">
        <h3>Vista Previa de Ficha de Proveedor</h3>
        <div className="preview-card">
          <div className="preview-header">
            <h4>{formData.nombre || "Nuevo Proveedor"}</h4>
            <span className={`badge-estado ${formData.activo ? "activo" : "inactivo"}`}>
              {formData.activo ? "Activo" : "Inactivo"}
            </span>
          </div>
          <div className="preview-body">
            <p className="preview-row">
              👤 <strong>Contacto:</strong> {formData.contacto || "No especificado"}
            </p>
            <p className="preview-row">
              📞 <strong>Teléfono:</strong> {formData.telefono || "No especificado"}
            </p>
            <p className="preview-row">
              ✉️ <strong>Email:</strong> {formData.email || "No especificado"}
            </p>
            <p className="preview-row">
              📍 <strong>Dirección:</strong> {formData.direccion || "No especificada"}
            </p>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FormularioProveedor;
