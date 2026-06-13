import { useEffect, useState } from "react";

function Categorias() {
  const [categorias, setCategorias] = useState([]);
  const [nombre, setNombre] = useState("");
  const [descripcion, setDescripcion] = useState("");

  const cargarCategorias = async () => {
    try {
      const respuesta = await fetch(
        "http://localhost:8080/api/categorias"
      );

      const datos = await respuesta.json();

      datos.sort((a, b) =>
        a.nombre.localeCompare(b.nombre)
      );

      setCategorias(datos);

    } catch (error) {
      console.error(error);
    }
  };

  useEffect(() => {
    cargarCategorias();
  }, []);

  const guardarCategoria = async () => {
    const categoria = {
      nombre,
      descripcion,
      nivel: 1,
      activo: true
    };

    await fetch(
      "http://localhost:8080/api/categorias",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(categoria)
      }
    );

    setNombre("");
    setDescripcion("");

    cargarCategorias();
  };

  return (
    <div>
      <h2>Categorías</h2>

      <input
        placeholder="Nombre"
        value={nombre}
        onChange={(e) =>
          setNombre(e.target.value)
        }
      />

      <input
        placeholder="Descripción"
        value={descripcion}
        onChange={(e) =>
          setDescripcion(e.target.value)
        }
      />

      <button onClick={guardarCategoria}>
        Agregar Categoría
      </button>

      <hr />

      <ul>
        {categorias.map((categoria) => (
          <li key={categoria.id}>
            <strong>{categoria.nombre}</strong>

            <br />

            {categoria.descripcion}
          </li>
        ))}
      </ul>
    </div>
  );
}

export default Categorias;