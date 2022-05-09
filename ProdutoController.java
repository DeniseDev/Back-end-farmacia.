package com.farmavida.FarmaVida.controller;

import java.util.List;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;



import com.farmavida.FarmaVida.model.Produto;
import com.farmavida.FarmaVida.repository.CategoriaRepository;
import com.farmavida.FarmaVida.repository.ProdutoRepository;

@RestController
@RequestMapping("/produtos")
@CrossOrigin
public class ProdutoController {
	
	@Autowired
	private ProdutoRepository produtoRe;
	
	
	@Autowired
	private CategoriaRepository categoriaRe;

	@GetMapping
	public ResponseEntity<List<Produto>> GetAll(){
		
		return ResponseEntity.ok(produtoRe.findAll()); }

	@GetMapping("/{id}")
		public ResponseEntity<Produto> getById(@PathVariable Long id){
			return produtoRe.findById(id)
					.map(resposta -> ResponseEntity.ok(resposta))
					.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
		}
			
	@GetMapping("/Produto/{nome}")
	public ResponseEntity<List<Produto>> getByNome(@PathVariable String Nome){
		return ResponseEntity.ok(produtoRe.findAllByNomeContainingIgnoreCase(Nome));

	}

	@PostMapping
	public ResponseEntity<Produto> post(@Valid @RequestBody Produto produto){
		if (categoriaRe.existsById(produto.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(produtoRe.save(produto));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

	}

	@PutMapping
	public ResponseEntity<Produto> put(@Valid @RequestBody Produto produto) {
		if(produtoRe.existsById(produto.getId())) {
			
		if(categoriaRe.existsById(produto.getCategoria().getId()))
		return ResponseEntity.status(HttpStatus. OK)
						.body(produtoRe.save(produto));
						
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
	}
	

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteProduto(@PathVariable Long id) {
		
		return produtoRe.findById(id)
				.map(resposta -> {
					produtoRe.deleteById(id);
					return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
				})
				.orElse(ResponseEntity.notFound().build());
	}

}

