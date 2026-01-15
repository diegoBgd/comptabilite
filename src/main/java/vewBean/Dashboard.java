package vewBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.donut.DonutChartDataSet;
import org.primefaces.model.charts.donut.DonutChartModel;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.pie.PieChartDataSet;
import org.primefaces.model.charts.pie.PieChartModel;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;

@ManagedBean
@ViewScoped
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;

	private int ventesJour = 125400;
	private int nombreClients = 45;
	private int encaissements = 67000;
	private int stockFaible = 12;

	private LineChartModel lineModel;
	private LineChartModel areaModel;
	private BarChartModel barModel;
	private DonutChartModel donutModel;
	private PieChartModel pieModel;

	private List<Sale> lastSales;

	public int getVentesJour() {
		return ventesJour;
	}

	public int getNombreClients() {
		return nombreClients;
	}

	public int getEncaissements() {
		return encaissements;
	}

	public int getStockFaible() {
		return stockFaible;
	}

	public LineChartModel getLineModel() {
		return lineModel;
	}

	public LineChartModel getAreaModel() {
		return areaModel;
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public DonutChartModel getDonutModel() {
		return donutModel;
	}

	public PieChartModel getPieModel() {
		return pieModel;
	}

	public List<Sale> getLastSales() {
		return lastSales;
	}

	@PostConstruct
	public void init() {
		createLineModel();
		createBarModel();
		createDonutModel();
		createPieModel();
		areaModel = createAreaModel();
		loadLastSales();
	}

	// ====================== LINE CHART ======================
	private void createLineModel() {
		// Créer le modèle
		lineModel = new LineChartModel();

		// ChartData
		ChartData data = new ChartData();

		// DataSet
		LineChartDataSet dataset = new LineChartDataSet();
		dataset.setLabel("Ventes (milliers)");
		dataset.setData(Arrays.asList(15, 20, 35, 50, 40, 60));
		dataset.setFill(false); // pas de zone remplie
		dataset.setBorderColor("rgb(75, 192, 192)");
		dataset.setTension(0.4); // arrondi

		data.addChartDataSet(dataset);
		data.setLabels(Arrays.asList("Jan", "Fév", "Mar", "Avr", "Mai", "Juin"));

		lineModel.setData(data);

		// Options
		LineChartOptions options = new LineChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();
		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // début à zéro
		scales.addYAxesData(yAxes);

		CartesianLinearAxes xAxes = new CartesianLinearAxes();
		scales.addXAxesData(xAxes);

		options.setScales(scales);

		// Ajouter animation / responsive
		// options.s.setResponsive(true);
		// options.setMaintainAspectRatio(false);

		lineModel.setOptions(options);
	}

	// ====================== AREA CHART ======================
	private LineChartModel createAreaModel() {
		// Créer le modèle
		LineChartModel model = new LineChartModel();

		// ChartData
		ChartData data = new ChartData();

		// DataSet
		LineChartDataSet dataset = new LineChartDataSet();
		dataset.setLabel("Ventes mensuelles");
		dataset.setData(Arrays.asList(15, 30, 20, 40, 50, 65));

		dataset.setFill(true); // zone remplie pour AreaChart
		dataset.setBorderColor("rgb(75, 192, 192)"); // couleur ligne
		dataset.setBackgroundColor("rgba(75, 192, 192, 0.4)"); // couleur remplissage
		dataset.setTension(0.4); // arrondi

		data.addChartDataSet(dataset);
		data.setLabels(Arrays.asList("Jan", "Fév", "Mar", "Avr", "Mai", "Juin"));

		model.setData(data);

		// Options
		LineChartOptions options = new LineChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();

		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // début à zéro
		scales.addYAxesData(yAxes);

		CartesianLinearAxes xAxes = new CartesianLinearAxes();
		scales.addXAxesData(xAxes);

		options.setScales(scales);

		// Responsive et ratio
		// options.setResponsive(true);
		// options.setMaintainAspectRatio(false);

		model.setOptions(options);

		return model;
	}

	// ====================== BAR CHART ======================
	private void createBarModel() {
		barModel = new BarChartModel();

		// ChartData
		ChartData data = new ChartData();

		// DataSet
		BarChartDataSet dataSet = new BarChartDataSet();
		dataSet.setLabel("Ventes par catégorie");
		dataSet.setData(Arrays.asList(120, 90, 150, 200));

		// Couleurs des barres
		dataSet.setBackgroundColor(Arrays.asList("rgba(54, 162, 235, 0.8)", "rgba(255, 99, 132, 0.8)",
				"rgba(255, 206, 86, 0.8)", "rgba(75, 192, 192, 0.8)"));

		data.addChartDataSet(dataSet);
		data.setLabels(Arrays.asList("Alimentaire", "Boissons", "Électronique", "Autres"));

		barModel.setData(data);

		// Options
		org.primefaces.model.charts.bar.BarChartOptions options = new org.primefaces.model.charts.bar.BarChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();
		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // commencer à zéro
		scales.addYAxesData(yAxes);
		options.setScales(scales);

		// options.setResponsive(true);
		// s options.setMaintainAspectRatio(false);

		barModel.setOptions(options);

		
	}

	// ====================== DONUT CHART ======================
	private void createDonutModel() {
		donutModel = new DonutChartModel();

		// ChartData
		ChartData data = new ChartData();

		// DataSet
		DonutChartDataSet dataSet = new DonutChartDataSet();
		dataSet.setData(Arrays.asList(35, 25, 20, 10, 10));

		// Couleurs des sections
		dataSet.setBackgroundColor(Arrays.asList("rgba(54, 162, 235, 0.8)", // Bleu
				"rgba(255, 99, 132, 0.8)", // Rouge
				"rgba(255, 206, 86, 0.8)", // Jaune
				"rgba(75, 192, 192, 0.8)", // Vert
				"rgba(153, 102, 255, 0.8)" // Violet
		));

		data.addChartDataSet(dataSet);

		// Labels
		data.setLabels(Arrays.asList("Électronique", "Vêtements", "Accessoires", "Maison", "Autres"));
		donutModel.setData(data);

	//	donutModel.setExtender("donutExtender");

	
	}

	// ====================== PIE CHART ======================
	private void createPieModel() {
		pieModel = new PieChartModel();

		// ChartData
		ChartData data = new ChartData();

		// DataSet
		PieChartDataSet dataSet = new PieChartDataSet();
		dataSet.setData(Arrays.asList(540, 120, 90));

		// Couleurs des sections
		dataSet.setBackgroundColor(Arrays.asList("rgb(54, 162, 235)", // Bleu
				"rgb(255, 99, 132)", // Rouge
				"rgb(255, 206, 86)" // Jaune
		));

		data.addChartDataSet(dataSet);

		// Labels
		data.setLabels(Arrays.asList("Ventes", "Achats", "Stock"));

		pieModel.setData(data);

		// Options
		org.primefaces.model.charts.pie.PieChartOptions options = new org.primefaces.model.charts.pie.PieChartOptions();
		// options.setResponsive(true);
		// options.setMaintainAspectRatio(false);

		
		pieModel.setOptions(options);

	
	}

	// ====================== LAST SALES ======================
	private void loadLastSales() {
		lastSales = new ArrayList<>();
		lastSales.add(new Sale("2025-01-01", "Client A", 150000));
		lastSales.add(new Sale("2025-01-02", "Client B", 98000));
		lastSales.add(new Sale("2025-01-03", "Client C", 120000));
		lastSales.add(new Sale("2025-01-04", "Client D", 45000));
		lastSales.add(new Sale("2025-01-05", "Client E", 76000));
	}

	public static class Sale {
		private String date;
		private String client;
		private double montant;

		public Sale(String date, String client, double montant) {
			this.date = date;
			this.client = client;
			this.montant = montant;
		}

		public String getDate() {
			return date;
		}

		public String getClient() {
			return client;
		}

		public double getMontant() {
			return montant;
		}
	}
}
