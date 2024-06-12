package com.bdd.web.stepdefinition;

import com.bdd.web.step.ReniecStep;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class ReniecStepDefintion {

    ReniecStep ReniecStep = new ReniecStep();


    @Given("^ingreso al sistema reniec \"([^\"]*)\"$")
    public void ElIngresoPagina(String IDTest) throws Throwable {
        ReniecStep.accederWeb(IDTest);
        System.out.println("fin given");
    }

    @And("^ingreso a la opcion peruanos extranjeros$")
    public void ingreso_a_la_opcion_peruanos_extranjeros() throws Throwable {
        ReniecStep.Clic_Boton_peruanos_extranjeros();
    }

    @And("^Despliega la opción Consulados en el mundo$")
    public void Despliega_la_opción_Consulados_en_el_mundo() throws Throwable {
        ReniecStep.Despliega_la_opcion_Consulados();

    }

    @And("^ingresar a la opción Lista de Consulados en el mundo$")
    public void ingresar_a_la_opción_Lista_de_Consulados_en_el_mundo() throws Throwable {
        ReniecStep.ingresar_a_la_opcion_lista();
    }

    @Then("^ingreso a la oficina a consultar \"([^\"]*)\"$")
    public void ingreso_a_la_oficina_a_consultar (String IDTest) throws Throwable {
        ReniecStep.ingresar_la_oficina_consultar(IDTest);
    }


















}