package com.bdd.web.step;

import com.bdd.web.page.ReniecPage;

public class ReniecStep {

    ReniecPage ReniecPage = new ReniecPage();

    public void accederWeb(String IDTest) throws Throwable {
        ReniecPage.accederWeb(IDTest);
    }

    public void  Clic_Boton_peruanos_extranjeros( ) throws Throwable {
        ReniecPage.Clic_Boton_extranjeros();
    }


    public void Despliega_la_opcion_Consulados() throws Throwable {
        ReniecPage.Despliega_opcion_Consulados();
    }

    public void ingresar_a_la_opcion_lista() throws Throwable {
        ReniecPage.Ingreso_opcion_lista();
    }

    public void ingresar_la_oficina_consultar(String IDTest) throws Throwable {
        ReniecPage.Ingreso_oficina_consultar(IDTest);
    }

   }








