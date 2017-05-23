package br.com.compraki.storage;

import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.compraki.dto.FotoDTO;

public class FotoStorageRunnable implements Runnable {

    private MultipartFile[] files;
    private DeferredResult<FotoDTO> resultado;
    private FotoStorage fotoStorage;

    public FotoStorageRunnable(MultipartFile[] files, DeferredResult<FotoDTO> resultado, FotoStorage fotoStorage) {
        this.files = files;
        this.resultado = resultado;
        this.fotoStorage = fotoStorage;
    }

    @Override
    public void run() {
        String nomeFoto = this.fotoStorage.salvarFoto(files);
        String contentType = files[0].getContentType();
        resultado.setResult(new FotoDTO(nomeFoto, contentType));
    }

}