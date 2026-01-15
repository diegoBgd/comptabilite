package entite;

public class Transaction {
    private String date;
    private String client;
    private int montant;
    private String statut;

    public Transaction(String d, String c, int m, String s) {
        date = d; client = c; montant = m; statut = s;
    }

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public int getMontant() {
		return montant;
	}

	public void setMontant(int montant) {
		this.montant = montant;
	}

	public String getStatut() {
		return statut;
	}

	public void setStatut(String statut) {
		this.statut = statut;
	}

    
}