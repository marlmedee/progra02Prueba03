/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package progra02prueba03;

/**
 *
 * @author marlo
 */
public abstract class Empleado implements Cloneable {
    private String codigo;
    private String nombre;
    private double salarioBase;

    public Empleado(String codigo, String nombre, double salarioBase) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
    }

    public String getCodigo(){ 
        return codigo;
    }
    public void setCodigo(String codigo){
        this.codigo = codigo;
    }
    public String getNombre(){
        return nombre;
    }
    public void setNombre(String nombre){ 
        this.nombre = nombre;
    }
    public double getSalarioBase(){ 
        return salarioBase;
    }
    public void setSalarioBase(double salBase){
        this.salarioBase = salBase;
    }
    public abstract double calcuSalario();
    public String mostrarInformacion(){
        return "codigo: "+codigo+" |nombre: "+nombre+" |salario base: "+salarioBase;
    }
    public String obtenerCategoria(){
        double salario = calcuSalario();
        if (salario < 1500){
            return "basico";
        }
        else if (salario <= 2500){
            return "estandar";
        }
        else{
            return "superior";
        }
    }

    @Override
    public Empleado clone() {
        try {
            return (Empleado) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("la clonacion no es correcta", e);
        }
    }
}

