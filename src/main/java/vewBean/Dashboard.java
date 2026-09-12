package vewBean;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.SessionFactory;
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

import entite.Encaissement;
import entite.Exercice;
import entite.ReglementClient;
import entite.User;
import model.EncaissementModel;
import model.ExerciceModel;
import model.ReglementClientModel;
import model.UserModel;
import persistances.DBConfiguration;
import utils.HelperC;

import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;

@ManagedBean
@ViewScoped
public class Dashboard implements Serializable {

	private static final long serialVersionUID = 1L;
	SessionFactory factory = DBConfiguration.getSessionFactory();
	EncaissementModel encModel;
	ReglementClientModel rcModel;
	Exercice selectdExercice;
	HttpSession session;
	String exerCode;
	String currUserCode;
	User currentUser;
	
	private String encaissement;
	private String decaissements;
	//private int encaissements = 67000;
	//private int stockFaible = 12;

	private LineChartModel lineModel;
	private LineChartModel areaModel;
	private BarChartModel barModel;
	private DonutChartModel donutModel;
	private PieChartModel pieModel;

	private List<Sale> lastSales;


	public String getEncaissement() {
		return encaissement;
	}

	public void setEncaissement(String encaissement) {
		this.encaissement = encaissement;
	}

	public String getDecaissements() {
		return decaissements;
	}

	public void setDecaissements(String decaissements) {
		this.decaissements = decaissements;
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
		chargementSession();
		encModel=new EncaissementModel();
		rcModel=new ReglementClientModel();
		getTotalEncaissement();
		createLineModel();
		createBarModel();
		createDonutModel();
		createPieModel();
		areaModel = createAreaModel();
		loadLastSales();
	}
	private void chargementSession() {
		this.session = HelperC.getSession();
		if (this.session != null) {
			this.exerCode = (String) this.session.getAttribute("exercice");
			this.currUserCode = (String) this.session.getAttribute("operateur");
		
			if (this.exerCode != null) {
				this.selectdExercice = (new ExerciceModel()).getExercByCode(this.factory, this.exerCode);
			}
			if (this.currUserCode != null) {
				this.currentUser = (new UserModel()).getUserByCode(this.factory, this.currUserCode);
			}

			if (this.currentUser == null || this.selectdExercice == null) {
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect("/comptabilite/login.xhtml");
				} catch (IOException e) {

					e.printStackTrace();
				}
			} 
		}
	}

	// ====================== LINE CHART ======================
	private void createLineModel() {
		// Cr�er le mod�le
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
		data.setLabels(Arrays.asList("Jan", "F�v", "Mar", "Avr", "Mai", "Juin"));

		lineModel.setData(data);

		// Options
		LineChartOptions options = new LineChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();
		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // d�but � z�ro
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
		// Cr�er le mod�le
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
		data.setLabels(Arrays.asList("Jan", "F�v", "Mar", "Avr", "Mai", "Juin"));

		model.setData(data);

		// Options
		LineChartOptions options = new LineChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();

		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // d�but � z�ro
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
		dataSet.setLabel("Ventes par cat�gorie");
		dataSet.setData(Arrays.asList(120, 90, 150, 200));

		// Couleurs des barres
		dataSet.setBackgroundColor(Arrays.asList("rgba(54, 162, 235, 0.8)", "rgba(255, 99, 132, 0.8)",
				"rgba(255, 206, 86, 0.8)", "rgba(75, 192, 192, 0.8)"));

		data.addChartDataSet(dataSet);
		data.setLabels(Arrays.asList("Alimentaire", "Boissons", "�lectronique", "Autres"));

		barModel.setData(data);

		// Options
		org.primefaces.model.charts.bar.BarChartOptions options = new org.primefaces.model.charts.bar.BarChartOptions();

		// Axes
		CartesianScales scales = new CartesianScales();
		CartesianLinearAxes yAxes = new CartesianLinearAxes();
		yAxes.setMin(0); // commencer � z�ro
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
		data.setLabels(Arrays.asList("�lectronique", "V�tements", "Accessoires", "Maison", "Autres"));
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

	private void getTotalEncaissement() {
		List<Encaissement>listEnc=encModel.getListEncaissement(factory, selectdExercice.getId(), null, null, null);
		List<ReglementClient> listRClt=rcModel.getListReglement(factory,  selectdExercice.getId(), null, null, null);
		double montantEncaisse=0;
		if(listEnc.size()>0)
		{
			for (Encaissement encaissement : listEnc) {
				montantEncaisse+=encaissement.getMontantTTC().doubleValue();
			}
		}
		if(listRClt.size()>0)
		{
			for (ReglementClient reglementClient : listRClt) {
				//montantEncaisse+=reglementClient.getMontantTTC().doubleValue();
			}
		}
		encaissement=HelperC.decimalNumber(montantEncaisse, 0, true);
		
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
