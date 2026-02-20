package dev.dvoa.yarl;

import net.minecraft.server.packs.repository.RepositorySource;

public interface IPackRepoMixin {
    void yarl$addPackFinder(RepositorySource packFinder);
}
