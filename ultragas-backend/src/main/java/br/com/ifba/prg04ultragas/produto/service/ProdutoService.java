package br.com.ifba.prg04ultragas.produto.service;

import br.com.ifba.prg04ultragas.infrastructure.exception.BusinessException;
import br.com.ifba.prg04ultragas.infrastructure.mapper.ObjectMapperUtil;
import br.com.ifba.prg04ultragas.produto.dto.ProdutoRequestDTO;
import br.com.ifba.prg04ultragas.produto.dto.ProdutoResponseDTO;
import br.com.ifba.prg04ultragas.produto.model.Produto;
import br.com.ifba.prg04ultragas.produto.repository.ProdutoRepository;
import br.com.ifba.prg04ultragas.log.service.LogService;

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

    @Autowired
    private LogService logService;

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

        logService.registrarAuditoria(
                "CRIACAO_PRODUTO",
                "Produto " + produto.getNome() + " criado.",
                "Produto",
                produto.getId(),
                null
        );

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

        logService.registrarAuditoria(
                "ATUALIZACAO_PRODUTO",
                "Produto " + produto.getNome() + " atualizado.",
                "Produto",
                produto.getId(),
                null
        );

        return mapper.map(produto, ProdutoResponseDTO.class);
    }

    @Transactional
    public void deletarProduto(Long id) {

        Produto produto = repository.findById(id)
                .orElseThrow(() ->
                        new BusinessException("Produto não encontrado"));

        logService.registrarAuditoria(
                "EXCLUSAO_PRODUTO",
                "Produto " + produto.getNome() + " excluído.",
                "Produto",
                produto.getId(),
                null
        );

        repository.delete(produto);
    }
}