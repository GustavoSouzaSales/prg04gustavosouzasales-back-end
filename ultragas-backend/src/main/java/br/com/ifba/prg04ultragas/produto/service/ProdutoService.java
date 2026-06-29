package br.com.ifba.prg04ultragas.produto.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04ultragas.produto.dto.ProdutoRequestDTO;
import br.com.ifba.prg04ultragas.produto.dto.ProdutoResponseDTO;
import br.com.ifba.prg04ultragas.produto.model.Produto;
import br.com.ifba.prg04ultragas.produto.repository.ProdutoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private ObjectMapperUtil mapper;

    public Page<ProdutoResponseDTO> listarProdutos(Pageable pageable) {

        Page<Produto> produtos = repository.findAll(pageable);

        return produtos.map(produto ->
                mapper.map(produto, ProdutoResponseDTO.class)
        );
    }

    public ProdutoResponseDTO buscarProdutoPorId(Long id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Produto não encontrado"));

        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional
    public ProdutoResponseDTO salvarProduto(ProdutoRequestDTO dto) {

        Produto produto = mapper.map(dto, Produto.class);

        if (produto.getAtivo() == null) {
            produto.setAtivo(true);
        }

        produto = repository.save(produto);

        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional
    public ProdutoResponseDTO atualizarProduto(Long id, ProdutoRequestDTO dto) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Produto não encontrado"));

        produto.setNome(dto.getNome());
        produto.setPeso(dto.getPeso());
        produto.setCor(dto.getCor());
        produto.setPreco(dto.getPreco());
        produto.setEstoque(dto.getEstoque());
        produto.setAtivo(dto.getAtivo());

        produto = repository.save(produto);

        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional
    public void deletarProduto(Long id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Produto não encontrado"));

        repository.delete(produto);
    }
}