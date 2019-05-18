/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lendingtreeapplication;

/**
 *
 * @author Sameeksha
 */
public class CustomerLoanDetailsListViewItem {

    int LoanId;
    String LoanName;
    int CustomerId;
    int LenderId;
    int LoanType;
    double Rateofinterest;
    double Tenureperiod;
    int Loanamounttaken;
    int Statusofloan;
    int lenderLoanId;

    public CustomerLoanDetailsListViewItem(int LoanId, int lenderLoanId, String LoanName, int CustomerId, int LenderId, int LoanType,
            double Rateofinterest, double Tenureperiod, int Loanamounttaken, int Statusofloan) {
        this.LoanId = LoanId;
        this.LoanName = LoanName;
        this.CustomerId = CustomerId;
        this.LenderId = LenderId;
        this.LoanType = LoanType;
        this.Rateofinterest = Rateofinterest;
        this.Tenureperiod = Tenureperiod;
        this.Loanamounttaken = Loanamounttaken;
        this.Statusofloan = Statusofloan;
        this.lenderLoanId = lenderLoanId;
    }

    public int getLenderLoanId() {
        return lenderLoanId;
    }

    public void setLenderLoanId(int lenderLoanId) {
        this.lenderLoanId = lenderLoanId;
    }

    
    public int getLoanId() {
        return LoanId;
    }

    public void setLoanId(int LoanId) {
        this.LoanId = LoanId;
    }

    public String getLoanName() {
        return LoanName;
    }

    public void setCustomerId(String LoanName) {
        this.LoanName = LoanName;
    }

    public int getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(int CustomerId) {
        this.CustomerId = CustomerId;
    }

    public int getLenderId() {
        return LenderId;
    }

    public void setLenderId(int LenderId) {
        this.LenderId = LenderId;
    }

    public int getLoanType() {
        return LoanType;
    }

    public void setLoanType(int LoanType) {
        this.LoanType = LoanType;
    }

    public double getRateofinterest() {
        return Rateofinterest;
    }

    public void setRateofinterest(double Rateofinterest) {
        this.Rateofinterest = Rateofinterest;
    }

    public double getTenureperiod() {
        return Tenureperiod;
    }

    public void setTenureperiod(double Tenureperiod) {
        this.Tenureperiod = Tenureperiod;
    }

    public int getLoanamounttaken() {
        return Loanamounttaken;
    }

    public void setLoanamounttaken(int Loanamounttaken) {
        this.Loanamounttaken = Loanamounttaken;
    }

    public int getStatusofloan() {
        return Statusofloan;
    }

    public void setStatusofloan(int Statusofloan) {
        this.Statusofloan = Statusofloan;
    }

    @Override
    public String toString() {
        return "CustomerLoanDetailsListViewItem{" + "LoanId=" + LoanId + ", LoanName=" + LoanName 
                + ", CustomerId=" + CustomerId + ", LenderId=" + LenderId + ", LoanType=" + LoanType 
                + ", Rateofinterest=" + Rateofinterest + ", Tenureperiod=" + Tenureperiod 
                + ", Loanamounttaken=" + Loanamounttaken + ", Statusofloan=" + Statusofloan 
                + ", lenderLoanId=" + lenderLoanId + '}';
    }
    
    
}
