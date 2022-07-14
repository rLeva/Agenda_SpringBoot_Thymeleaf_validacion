package com.agenda.contactos.controlador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.agenda.contactos.modelo.Contacto;
import com.agenda.contactos.repositorio.ContactoRepositorio;

@Controller
public class ContactoControlador {
	
	//Inyectamos el repositorio
	@Autowired
	private ContactoRepositorio repo;
	
	//Método para ver la web de inicio
	@GetMapping({"/",""})
	public String verPaginaDeInicio(Model model) {
		
		List<Contacto> contactos = repo.findAll();
		model.addAttribute("contactos", contactos);//el segundo contactos es la lista, para poder listar los contactos que hay en la lista que hemos creado.
		
		return "index";
	}
	
	//método para mostrar el formulario para crear nuevo contacto
	@GetMapping("/nuevo")
	public String mostrarFormularioRegistroContacto(Model model) {
		
		//paso un modelo que es un nuevo objeto al que le podremos asignar cada atributo en los diferentes campos
		model.addAttribute("contacto", new Contacto());
		
		return "nuevo";
		
	}
	
	//método para guardar nuevo contacto
	@PostMapping("/nuevo")
	public String guardarContacto(@Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model model) {
		
		//Hago la validacion del bindingResult, bindingResult tiene que ir colocado inmediatamente despues del objeto 
		//si no se hace asi no va a funcionar las validaciones.
		//si bindingResult tiene algun error,para obtener todos los errores y mostralos en el front end
		if(bindingResult.hasErrors()) {
			//si tiene errores le pasamos un nuevo formulario, el objeto contacto es para pasar el nuevo formulario
			model.addAttribute("contacto", contacto);
			return "nuevo";
		}
		
		//la anotacion validate nos valida el objeto que precede, en este caso Contacto contacto
		//BindingResult me va a obtener todos los errores
		repo.save(contacto);
		
		//con el objeto redirect nos sirve para poder enviarle mensajes
		redirect.addFlashAttribute("msgExito", "El contacto se añadió con éxito");
		
		return "redirect:/";
	}
	
	//método para mostrar el formulario para modificar contacto
		@GetMapping("/{id}/editar")
		public String mostrarFormularioModificarContacto(@PathVariable Integer id, Model model) {
			//Busco el contacto por su id
			Contacto contacto = repo.findById(id).get();
			//mando el contacto al formulario
			model.addAttribute("contacto", contacto);
			
			return "nuevo";
			
		}

		//método para modificar contacto
		@PostMapping("/{id}/editar")
		public String actualizarContacto(@PathVariable Integer id, @Validated Contacto contacto, BindingResult bindingResult, RedirectAttributes redirect, Model model) {
			//obtengo el contacto de la base de datos
			Contacto contactoDB = repo.findById(id).get();
			//Hago la validacion del bindingResult, bindingResult tiene que ir colocado inmediatamente despues del objeto 
			//si no se hace asi no va a funcionar las validaciones.
			//si bindingResult tiene algun error,para obtener todos los errores y mostralos en el front end
			if(bindingResult.hasErrors()) {
				//si tiene errores le pasamos un nuevo formulario, el objeto contacto es para pasar el nuevo formulario
				model.addAttribute("contacto", contacto);
				return "nuevo";
			}
			
			//establezco los nuevos datos
			contactoDB.setNombre(contacto.getNombre());
			contactoDB.setTelefono(contacto.getTelefono());
			contactoDB.setEmail(contacto.getEmail());
			contactoDB.setFechaNacimiento(contacto.getFechaNacimiento());
			
			
			//la anotacion validate nos valida el objeto que precede, en este caso Contacto contacto
			//BindingResult me va a obtener todos los errores
			repo.save(contactoDB);
			
			//con el objeto redirect nos sirve para poder enviarle mensajes
			redirect.addFlashAttribute("msgExito", "El contacto se modificó con éxito");
			
			return "redirect:/";
		}
		
		//método eliminar contacto
		@PostMapping("/{id}/eliminar")
		public String elimarContacto(@PathVariable Integer id, RedirectAttributes redirect) {
			//Busco el contacto por su id
			Contacto contacto = repo.findById(id).get();
			//elimino el contacto
			repo.delete(contacto);
			//mando el mensaje de contacto eliminado con exito
			redirect.addFlashAttribute("msgExito", "El contacto ha sido eliminado con éxito");
			return "redirect:/";
			
		}
}
