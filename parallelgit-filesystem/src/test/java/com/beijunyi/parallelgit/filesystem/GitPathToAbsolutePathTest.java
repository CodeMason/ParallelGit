package com.beijunyi.parallelgit.filesystem;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class GitPathToAbsolutePathTest extends PreSetupGitFileSystemTest {

  @Test
  public void absolutePathToAbsolutePathTest() {
    GitPath path = gfs.getPath("/a/b");
    GitPath result = path.toAbsolutePath();
    assertEquals("/a/b", result.toString());
  }

  @Test
  public void rootPathToAbsolutePathTest() {
    GitPath path = gfs.getPath("/");
    GitPath result = path.toAbsolutePath();
    assertEquals("/", result.toString());
  }

  @Test
  public void relativePathToAbsolutePathTest() {
    GitPath path = gfs.getPath("a/b");
    GitPath result = path.toAbsolutePath();
    assertEquals("/a/b", result.toString());
  }

  @Test
  public void emptyPathToAbsolutePathTest() {
    GitPath path = gfs.getPath("");
    GitPath result = path.toAbsolutePath();
    assertEquals("/", result.toString());
  }
}
