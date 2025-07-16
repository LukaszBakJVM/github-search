package org.lukasz.githubsercher.model;

import java.util.List;

public record Repository(String name, Owner owner, boolean fork, List<Branch> branches) {
}
