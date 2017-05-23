package br.com.compraki.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FotoStorage {

    public String salvarFoto(MultipartFile[] files);

    public byte[] recuperarFoto(String nome);

}