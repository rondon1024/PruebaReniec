@Reniec
Feature: Lista de consulados

  @Login
  Scenario Outline: reniec
    Given ingreso al sistema reniec "<IDTest>"
    When ingreso a la opcion peruanos extranjeros
    And Despliega la opción Consulados en el mundo
    And ingresar a la opción Lista de Consulados en el mundo
    Then ingreso a la oficina a consultar "<IDTest>"
    Examples:
      | IDTest |
      | 1      |










