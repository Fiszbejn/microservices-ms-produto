package com.github.cidarosa.ms.produto.service;

import com.github.cidarosa.ms.produto.dto.ProdutoRequestDTO;
import com.github.cidarosa.ms.produto.dto.ProdutoResponseDTO;
import com.github.cidarosa.ms.produto.entities.Categoria;
import com.github.cidarosa.ms.produto.entities.Produto;
import com.github.cidarosa.ms.produto.exceptions.DatabaseException;
import com.github.cidarosa.ms.produto.exceptions.ResourceNotFoundException;
import com.github.cidarosa.ms.produto.repositories.CategoriaRepository;
import com.github.cidarosa.ms.produto.repositories.ProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional(readOnly = true)
    public List<ProdutoResponseDTO> findAllProdutos(){

        List<Produto> produtos = produtoRepository.findAll();

        return produtos.stream().map(ProdutoResponseDTO::new).toList();
    }

    @Transactional(readOnly = true)
    public ProdutoResponseDTO findProdutoById(Long id){

        Produto produto = produtoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Recurso não encontrado. ID: " + id)
        );

        return new ProdutoResponseDTO(produto);
    }

    @Transactional
    public ProdutoResponseDTO saveProduto(ProdutoRequestDTO requestDTO){

        Produto produto = new Produto();
        copyDtoToProduto(requestDTO, produto);
        produto = produtoRepository.save(produto);
        return new ProdutoResponseDTO(produto);

    }

    @Transactional
    public ProdutoResponseDTO updateProduto(Long id, ProdutoRequestDTO requestDTO){

        try {
            Produto produto = produtoRepository.getReferenceById(id);
            copyDtoToProduto(requestDTO, produto);
            produto = produtoRepository.save(produto);
            return new ProdutoResponseDTO(produto);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }
    }

    public void deleteProdutoById(Long id){

        if(!produtoRepository.existsById(id)){
            throw new ResourceNotFoundException("Recurso não encontrado. ID: " + id);
        }

        produtoRepository.deleteById(id);
    }

    private void copyDtoToProduto(ProdutoRequestDTO requestDTO, Produto produto) {

        produto.setNome(requestDTO.getNome());
        produto.setDescricao(requestDTO.getDescricao());
        produto.setValor(requestDTO.getValor());

//        Categoria categoria = categoriaRepository.getReferenceById(requestDTO.getCategoria().getId());

        Categoria categoria = categoriaRepository.findById(requestDTO.getCategoriaId()).orElseThrow(
                () -> new DatabaseException(("Não foi possível salvar Produto. Categoria inexistente. ID: " + requestDTO.getCategoriaId()))
        );

        produto.setCategoria(categoria);
    }
}
