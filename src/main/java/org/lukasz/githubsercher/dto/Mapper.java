package org.lukasz.githubsercher.dto;

import org.lukasz.githubsercher.model.Branch;
import org.lukasz.githubsercher.model.Commit;
import org.lukasz.githubsercher.model.Owner;
import org.lukasz.githubsercher.model.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Mapper {


    public RepositoryDto fromRepositoryToDto(Repository repository) {
        List<BranchDto> branchDtoList = repository.branches().stream().map(this::fromBranchToDto).toList();
        OwnerDto ownerDto = fromOwnerToDto(repository.owner());
        return new RepositoryDto(repository.name(), ownerDto, repository.fork(), branchDtoList);
    }

    private BranchDto fromBranchToDto(Branch branch) {
        return new BranchDto(branch.name(), fromCommitToDto(branch.commit()));
    }

    private OwnerDto fromOwnerToDto(Owner owner) {
        return new OwnerDto(owner.login());
    }

    private CommitDto fromCommitToDto(Commit commit) {
        return new CommitDto(commit.sha());
    }
}
