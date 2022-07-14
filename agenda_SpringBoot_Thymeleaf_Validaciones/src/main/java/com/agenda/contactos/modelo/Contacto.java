package com.agenda.contactos.modelo;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
public class Contacto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	//validaciones
	@NotBlank(message="Debe ingresar su nombre")//la anotación NotBlank no permite campos en blanco, solo se aplica a String.
	private String nombre;
	
	@NotEmpty(message="Debe ingresar su email")//la anotación NotEmpty es que no puede estar vació el campo, se aplica tanto a string como collections, map o arrays.
	@Email
	private String email;
	
	@NotBlank(message="Debe ingresar un numero de telefono")
	private String telefono;
	
	@DateTimeFormat(iso=ISO.DATE)//la anotación para darle formato a la fecha
	@Past//la anotación para indicarle que las fechas ha de ser pasadas
	@NotNull(message="Tiene que ingresar una fecha de nacimiento")//la anotación para que no permita datos nulos
	private LocalDate fechaNacimiento;
	
	private LocalDateTime fechaRegistro;
	
	@PrePersist//esta anotacion persiste el metodo, cuando cree un objeto por defecto le va a asignar la fecha actual.
	public void asignarFechaRegistro() {
		fechaRegistro = LocalDateTime.now();
	}

	public Contacto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Contacto(Integer id, String nombre, String email, String telefono, LocalDate fechaNacimiento,
			LocalDateTime fechaRegistro) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.telefono = telefono;
		this.fechaNacimiento = fechaNacimiento;
		this.fechaRegistro = fechaRegistro;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public LocalDateTime getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(LocalDateTime fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}
	
	
	

}
