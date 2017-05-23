package br.com.compraki.storage.local;

import static java.nio.file.FileSystems.getDefault;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import br.com.compraki.storage.FotoStorage;

public class FotoStorageLocal implements FotoStorage {

	private static String system;

	private Path local;

	public FotoStorageLocal() {
		this(getDefault().getPath(
				getSystem().contains("Windows") ? System.getenv("USERPROFILE") : System.getenv("HOME"), ".photouser"));
	}

	public FotoStorageLocal(Path path) {
		this.local = path;
		criarPastas();
	}

	@Override
	public String salvarFoto(MultipartFile[] files) {
		String novoNome = null;
		if (files != null && files.length > 0) {
			MultipartFile arquivo = files[0];
			novoNome = renomearArquivo(arquivo.getOriginalFilename());
			try {
				String path = this.local.toAbsolutePath().toString() + getDefault().getSeparator() + novoNome;
				arquivo.transferTo(new File(path));
			} catch (IOException e) {
				throw new RuntimeException("Erro salvando a foto na pasta temporária", e);
			}
		}

		return novoNome;
	}

	@Override
	public byte[] recuperarFoto(String nome) {
		try {
			return Files.readAllBytes(this.local.resolve(nome));
		} catch (IOException e) {
			throw new RuntimeException("Erro lendo a foto", e);
		}
	}

	private void criarPastas() {
		try {
			Files.createDirectories(this.local);
			this.local = getDefault().getPath(this.local.toString());
			Files.createDirectories(this.local);
		} catch (IOException e) {
			throw new RuntimeException("Erro criando pasta para salvar foto", e);
		}
	}

	private String renomearArquivo(String nomeOriginal) {
		String novoNome = UUID.randomUUID().toString() + "_" + nomeOriginal;
		return novoNome;
	}

	public static String getSystem() {
		if (system == null) {
			system = System.getProperty("os.name");
		}
		return system;
	}
}