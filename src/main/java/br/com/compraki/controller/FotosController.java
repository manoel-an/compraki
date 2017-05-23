package br.com.compraki.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import br.com.compraki.dto.FotoDTO;
import br.com.compraki.storage.FotoStorage;
import br.com.compraki.storage.FotoStorageRunnable;

@RestController
@RequestMapping("/fotos")
public class FotosController {

    @Autowired
    private FotoStorage fotoStorage;

    @RequestMapping(method = RequestMethod.POST)
    public DeferredResult<FotoDTO> upload(@RequestParam("files[]") MultipartFile[] files) {
        DeferredResult<FotoDTO> resultado = new DeferredResult<>();

        Thread thread = new Thread(new FotoStorageRunnable(files, resultado, fotoStorage));
        thread.start();

        return resultado;
    }

    @RequestMapping(value = "/{nome:.*}", method = RequestMethod.GET)
    public byte[] recuperarFoto(@PathVariable String nome) {
        return fotoStorage.recuperarFoto(nome);
    }

}