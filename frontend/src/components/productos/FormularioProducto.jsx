import { useState } from "react";
import "./FormularioProducto.css";

const FormularioProducto = ({ onProductoAgregado }) => {
  const [formData, setFormData] = useState({
    nombre: "Polo Deportivo Premium",
    descripcion: "Polo de alta calidad para actividades deportivas",
    precio_unitario: 29.99,
    sku: "PLO-DEPO-001",
    tallaId: 14,
    estado: "ACTIVO",
    cantidad_inicial: 20,
    stock_minimo: 10,
  });

  const [errores, setErrores] = useState({});
  const [enviado, setEnviado] = useState(false);
  const [cargando, setCargando] = useState(false);

  const tallas = [
    { id: 10, nombre: "S", categoria: "INFANTIL" },
    { id: 14, nombre: "M", categoria: "ADULTO" },
    { id: 15, nombre: "L", categoria: "ADULTO" },
    { id: 16, nombre: "XL", categoria: "ADULTO" },
  ];

  // Validación del formulario
  const validarFormulario = () => {
    const nuevosErrores = {};

    if (!formData.nombre.trim()) {
      nuevosErrores.nombre = "El nombre es requerido";
    }
    if (!formData.sku.trim()) {
      nuevosErrores.sku = "El SKU es requerido";
    }
    if (formData.precio_unitario <= 0) {
      nuevosErrores.precio_unitario = "El precio debe ser mayor a 0";
    }
    if (formData.cantidad_inicial < 0) {
      nuevosErrores.cantidad_inicial = "La cantidad no puede ser negativa";
    }

    setErrores(nuevosErrores);
    return Object.keys(nuevosErrores).length === 0;
  };

  // Manejar cambios en el formulario
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: name.includes("precio") || name.includes("cantidad")
        ? parseFloat(value) || 0
        : value,
    }));
  };

  // Simular POST
  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!validarFormulario()) {
      return;
    }

    setCargando(true);

    // Simular envío a API (después se conectará con el backend real)
    try {
      const nuevoProducto = {
        id_producto: Math.floor(Math.random() * 10000),
        nombre: formData.nombre,
        descripcion: formData.descripcion,
        sku: formData.sku,
        precio_unitario: formData.precio_unitario,
        estado: formData.estado,
        talla: tallas.find((t) => t.id === parseInt(formData.tallaId)),
        inventario: {
          cantidad_actual: formData.cantidad_inicial,
          stock_minimo: formData.stock_minimo,
          stock_maximo: formData.cantidad_inicial * 2,
        },
      };

      // Simular respuesta del servidor (200 ms de delay)
      await new Promise((resolve) => setTimeout(resolve, 200));

      console.log("✅ Producto agregado:", nuevoProducto);

      // Mostrar confirmación
      setEnviado(true);
      setTimeout(() => setEnviado(false), 3000);

      // Notificar al componente padre
      if (onProductoAgregado) {
        onProductoAgregado(nuevoProducto);
      }

      // Limpiar formulario
      setFormData({
        nombre: "Polo Deportivo Premium",
        descripcion: "Polo de alta calidad para actividades deportivas",
        precio_unitario: 29.99,
        sku: "PLO-DEPO-001",
        tallaId: 14,
        estado: "ACTIVO",
        cantidad_inicial: 20,
        stock_minimo: 10,
      });
      setErrores({});
    } catch (error) {
      console.error("❌ Error al agregar producto:", error);
    } finally {
      setCargando(false);
    }
  };

  return (
    <div className="formulario-producto-container">
      <div className="formulario-header">
        <h2>Agregar Nuevo Producto</h2>
        <p>Complete el formulario para registrar un nuevo artículo en el inventario</p>
      </div>

      {/* Alerta de éxito */}
      {enviado && (
        <div className="alerta alerta-exito">
          ✅ ¡Producto agregado exitosamente!
        </div>
      )}

      <form className="producto-form" onSubmit={handleSubmit}>
        {/* Sección 1: Información Básica */}
        <fieldset className="form-fieldset">
          <legend>Información Básica</legend>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="nombre">Nombre del Producto *</label>
              <input
                type="text"
                id="nombre"
                name="nombre"
                value={formData.nombre}
                onChange={handleChange}
                placeholder="Ej: Camiseta Básica"
                className={errores.nombre ? "error" : ""}
              />
              {errores.nombre && (
                <span className="error-msg">{errores.nombre}</span>
              )}
            </div>

            <div className="form-grupo">
              <label htmlFor="sku">SKU (Código) *</label>
              <input
                type="text"
                id="sku"
                name="sku"
                value={formData.sku}
                onChange={handleChange}
                placeholder="Ej: TSH-BLU-S"
                className={errores.sku ? "error" : ""}
              />
              {errores.sku && (
                <span className="error-msg">{errores.sku}</span>
              )}
            </div>
          </div>

          <div className="form-grupo">
            <label htmlFor="descripcion">Descripción</label>
            <textarea
              id="descripcion"
              name="descripcion"
              value={formData.descripcion}
              onChange={handleChange}
              placeholder="Descripción detallada del producto..."
              rows="3"
            ></textarea>
          </div>
        </fieldset>

        {/* Sección 2: Precio y Talla */}
        <fieldset className="form-fieldset">
          <legend>Precio y Características</legend>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="precio_unitario">Precio Unitario ($) *</label>
              <input
                type="number"
                id="precio_unitario"
                name="precio_unitario"
                value={formData.precio_unitario}
                onChange={handleChange}
                min="0"
                step="0.01"
                className={errores.precio_unitario ? "error" : ""}
              />
              {errores.precio_unitario && (
                <span className="error-msg">{errores.precio_unitario}</span>
              )}
            </div>

            <div className="form-grupo">
              <label htmlFor="tallaId">Talla *</label>
              <select
                id="tallaId"
                name="tallaId"
                value={formData.tallaId}
                onChange={handleChange}
              >
                {tallas.map((talla) => (
                  <option key={talla.id} value={talla.id}>
                    {talla.nombre} ({talla.categoria})
                  </option>
                ))}
              </select>
            </div>
          </div>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="estado">Estado del Producto</label>
              <select
                id="estado"
                name="estado"
                value={formData.estado}
                onChange={handleChange}
              >
                <option value="ACTIVO">Activo</option>
                <option value="INACTIVO">Inactivo</option>
              </select>
            </div>
          </div>
        </fieldset>

        {/* Sección 3: Inventario */}
        <fieldset className="form-fieldset">
          <legend>Inventario Inicial</legend>

          <div className="form-row">
            <div className="form-grupo">
              <label htmlFor="cantidad_inicial">Cantidad Inicial *</label>
              <input
                type="number"
                id="cantidad_inicial"
                name="cantidad_inicial"
                value={formData.cantidad_inicial}
                onChange={handleChange}
                min="0"
                step="1"
                className={errores.cantidad_inicial ? "error" : ""}
              />
              {errores.cantidad_inicial && (
                <span className="error-msg">{errores.cantidad_inicial}</span>
              )}
            </div>

            <div className="form-grupo">
              <label htmlFor="stock_minimo">Stock Mínimo *</label>
              <input
                type="number"
                id="stock_minimo"
                name="stock_minimo"
                value={formData.stock_minimo}
                onChange={handleChange}
                min="0"
                step="1"
              />
            </div>
          </div>
        </fieldset>

        {/* Botones */}
        <div className="form-acciones">
          <button
            type="submit"
            className="btn-guardar"
            disabled={cargando}
          >
            {cargando ? "Procesando..." : "💾 Guardar Producto"}
          </button>
          <button type="reset" className="btn-limpiar">
            🔄 Limpiar Formulario
          </button>
        </div>
      </form>

      {/* Vista previa */}
      <div className="preview-section">
        <h3>Vista Previa del Producto</h3>
        <div className="preview-card">
          <div className="preview-header">
            <h4>{formData.nombre}</h4>
            <span className={`badge-estado ${formData.estado.toLowerCase()}`}>
              {formData.estado}
            </span>
          </div>
          <p className="preview-sku">SKU: <code>{formData.sku}</code></p>
          <p className="preview-desc">{formData.descripcion}</p>
          <div className="preview-footer">
            <div className="preview-info">
              <span className="label">Precio:</span>
              <span className="valor precio">${formData.precio_unitario.toFixed(2)}</span>
            </div>
            <div className="preview-info">
              <span className="label">Talla:</span>
              <span className="valor">
                {tallas.find((t) => t.id === parseInt(formData.tallaId))?.nombre}
              </span>
            </div>
            <div className="preview-info">
              <span className="label">Stock Inicial:</span>
              <span className="valor">{formData.cantidad_inicial} unidades</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default FormularioProducto;
