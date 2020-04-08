package myassessment.DB;

public interface IObjectRepository {
	
	final String selectAllCountriesCapital = "select country from countries where Capital  IS  NULL";
	final String countryColumn = "country";
	
	final String selectAllCountriesDataMissing = "select country from countries where ( Capital and Currency ) IS  NULL";
	
	final String selectAllCountriesBorders = "select C.Country from countries AS C Inner join (select C_ID from Borders group by C_ID order by count(B_ID) desc limit 1) AS B ON C.C_ID = B.C_ID";
	
	
	

}
