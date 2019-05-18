/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author prith
 */
public class LoanDetailsListViewItem {

    int loanId;
    int lenderId;
    int loanType;
    String loanProviderName;
    double rateOfInterest;
    double tenurePeriod;
    String loanName;
    int maxAmountProvided;
    
    public LoanDetailsListViewItem(int loanId, int lenderId, int loanType, String loanProviderName, double rateOfInterest, double tenurePeriod,
            String loanName, int maxAmountProvided) {
        this.loanProviderName = loanProviderName;
        this.rateOfInterest = rateOfInterest;
        this.tenurePeriod = tenurePeriod;
        this.loanName = loanName;
        this.maxAmountProvided = maxAmountProvided;
        this.lenderId = lenderId;
        this.loanType = loanType;
        this.loanId = loanId;
    }

    public int getLoanId() {
        return loanId;
    }

    public void setLoanId(int loanId) {
        this.loanId = loanId;
    }
    
    public int getLoanType() {
        return loanType;
    }

    public void setLoanType(int loanType) {
        this.loanType = loanType;
    }

    public int getLenderId() {
        return lenderId;
    }

    public void setLenderId(int lenderId) {
        this.lenderId = lenderId;
    }

    public String getLoanProviderName() {
        return loanProviderName;
    }

    public void setLoanProviderName(String loanProviderName) {
        this.loanProviderName = loanProviderName;
    }

    public double getRateOfInterest() {
        return rateOfInterest;
    }

    public void setRateOfInterest(double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public double getTenurePeriod() {
        return tenurePeriod;
    }

    public void setTenurePeriod(double tenurePeriod) {
        this.tenurePeriod = tenurePeriod;
    }

    public String getLoanName() {
        return loanName;
    }

    public void setLoanName(String loanName) {
        this.loanName = loanName;
    }

    public int getMaxAmountProvided() {
        return maxAmountProvided;
    }

    public void setMaxAmountProvided(int maxAmountProvided) {
        this.maxAmountProvided = maxAmountProvided;
    }

    @Override
    public String toString() {
        return "LoanDetailsListViewItem{" + "lenderId=" + lenderId
                + ", loanType=" + loanType + ", loanProviderName=" + loanProviderName
                + ", rateOfInterest=" + rateOfInterest + ", tenurePeriod=" + tenurePeriod
                + ", loanName=" + loanName + ", maxAmountProvided=" + maxAmountProvided + "}";
    }
}
