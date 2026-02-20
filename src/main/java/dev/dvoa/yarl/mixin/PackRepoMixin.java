package dev.dvoa.yarl.mixin;

import dev.dvoa.yarl.IPackRepoMixin;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;

@Mixin(PackRepository.class)
public abstract class PackRepoMixin implements IPackRepoMixin {
    @Shadow
    private Set<RepositorySource> sources;

    @Override
    public void yarl$addPackFinder(RepositorySource packFinder) {
        this.sources.add(packFinder);
    }
}
