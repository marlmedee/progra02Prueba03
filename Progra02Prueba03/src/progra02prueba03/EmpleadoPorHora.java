/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra02prueba03;

/**
 *
 * @author marlo
 */
public class EmpleadoPorHora extends Empleado{
    private int horasTrabajadas;
    private double tarifaPorHora;

    public EmpleadoPorHora(String codigo, String nombre, double salarioBase, int horasTrabajadas, double tarifaPorHora) {
        super(codigo, nombre, salarioBase);
        this.horasTrabajadas = horasTrabajadas;
        this.tarifaPorHora = tarifaPorHora;
    }
    
    public int getHorasTrabajadas() {
        return horasTrabajadas;
    }
    public double getTarifaPorHora() {
        return tarifaPorHora;
    }
    @Override
    public double calcuSalario(){
        return getSalarioBase()+(horasTrabajadas*tarifaPorHora);
    }
}
