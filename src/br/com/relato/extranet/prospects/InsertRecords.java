package br.com.relato.extranet.prospects;


import br.com.relato.extranet.model.Prospect;
import java.util.List;
import java.util.ArrayList;
import br.com.relato.xls.util.ImportUtils;
import br.com.neorelato.sql.Table;
import net.sf.hibernate.*;
import br.com.relato.EntryPoint;

public class InsertRecords {

	/**
	 * @param args
	 */
	Prospect prospect;
	Session session;
	Transaction tx = null;
	String empresa = "";
	String contato = "";
	String email = "";
	List listRecords;
	
	public InsertRecords(String path){
		try{
			List listFields = new ArrayList();
			listFields.add("EMPRESA");
			listFields.add("CONTATO");
			listFields.add("E-MAIL");
			//System.out.println("Vamos ler o excel campos == "+listFields);
			Table tbRecords = ImportUtils.getExcel(path,listFields);
			System.out.println("Excel table tamanho == "+tbRecords.rowCount());
			if(tbRecords.rowCount() > 0){
				tbRecords.first();
				this.session = EntryPoint.getHbmsession();
				tx = session.beginTransaction();
				while(!tbRecords.eof()){
					this.empresa = (null != tbRecords.getString("EMPRESA") ? tbRecords.getString("EMPRESA") : "" );
					this.contato = (null != tbRecords.getString("CONTATO") ? tbRecords.getString("CONTATO") : "" );
					this.email = (null != tbRecords.getString("E-MAIL") ? tbRecords.getString("E-MAIL") : "" );
					if(!"".equals(email.trim())){
						this.listRecords = session.find("from Prospect p where p.email = '"+email.trim().toLowerCase()+"'");
						if(listRecords.size() == 0){
							prospect = new Prospect();
							prospect.setEmail(this.email.trim().toLowerCase());
							prospect.setNome(this.contato.trim().toUpperCase());
							prospect.setEmpresa(this.contato.trim().toUpperCase());
							prospect.setAtivo("S");
							prospect.setDescadastre("N");
							session.save(prospect);
							session.flush();
						}
					}
					tbRecords.next();
				}
				tx.commit();
				
			}
			System.out.println("terminei excel");
		}catch(Exception e){
			System.out.println("PROBLEMA AO INCLUIR PROSPECTS");
			e.printStackTrace();
			System.out.println("CORRIGIR PROSPECTS");
		}
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
