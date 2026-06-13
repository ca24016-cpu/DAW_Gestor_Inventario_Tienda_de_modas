import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
} from "chart.js";

import { Bar } from "react-chartjs-2";

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);

function GraficoInventario({ inventario }) {

  const data = {
    labels: inventario.map(
      item => item.productoNombre
    ),
    datasets: [
      {
        label: "Stock Actual",
        data: inventario.map(
          item => item.cantidadActual
        )
      }
    ]
  };

  return (
    <div style={{ width: "90%", margin: "20px auto" }}>
      <h3>Stock por Producto</h3>
      <Bar data={data} />
    </div>
  );
}

export default GraficoInventario;