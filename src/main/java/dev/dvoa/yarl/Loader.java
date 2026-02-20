package dev.dvoa.yarl;

import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.FolderRepositorySource;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.world.level.validation.DirectoryValidator;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Loader {
    private static final Path repoPath = Path.of("config", "YARL", "packs");

    public static List<String> listPacks(Path folder) {
        List<String> packs = new ArrayList<>();
        File[] files = folder.toFile().listFiles();
        if (files == null)
            return Collections.emptyList();

        for (File fileEntry : files) {
            if (!fileEntry.isDirectory())
                packs.add("file/" + fileEntry.getName().strip());
        }
        return packs;
    }

    public static void loadResources() {
        Minecraft mc = Minecraft.getInstance();
        PackRepository repo = mc.getResourcePackRepository();
        Options options = mc.options;

        //Load new packs and make directory
        addPackFinder();

        //Get already selected packs
        List<String> selected = new ArrayList<>(repo.getSelectedIds());

        //Add packs
        List<String> packs = listPacks(repoPath);

        for (Pack pack : repo.getAvailablePacks()) {
            if (packs.contains(pack.getId()) && pack.getPackSource() == PackSource.BUILT_IN) {
                selected.add(pack.getId());
                options.resourcePacks.add(pack.getId());
            }
        }

        // Apply and reload
        repo.setSelected(selected);
        options.save();
        mc.reloadResourcePacks();
    }

    public static void addPackFinder() {
        if (!repoPath.toFile().exists())
            repoPath.toFile().mkdirs();
        Minecraft mc = Minecraft.getInstance();
        PackRepository repo = mc.getResourcePackRepository();

        PathMatcher allowAll = FileSystems.getDefault().getPathMatcher("glob:**");
        DirectoryValidator validator = new DirectoryValidator(allowAll);

        FolderRepositorySource frs = new FolderRepositorySource(
                repoPath,
                PackType.CLIENT_RESOURCES,
                PackSource.BUILT_IN,
                validator
        );
        ((IPackRepoMixin) repo).yarl$addPackFinder(frs);
        repo.reload();
    }
}
