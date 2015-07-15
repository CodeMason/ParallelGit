package com.beijunyi.parallelgit.filesystem.utils;

import java.net.URI;
import java.nio.file.ProviderMismatchException;
import java.util.*;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.beijunyi.parallelgit.filesystem.GitFileSystemProvider;

public final class GitUriUtils {

  public final static String SID_KEY = "sid";

  /**
   * Checks if the scheme of the given {@code URI} is equal (without regard to case) to {@link
   * com.beijunyi.parallelgit.filesystem.GitFileSystemProvider#GIT_FS_SCHEME}.
   *
   * @param   uri
   *          the {@code URI} to check
   * @throws  ProviderMismatchException
   *          if the given {@code URI} is not equal to {@link com.beijunyi.parallelgit.filesystem.GitFileSystemProvider#GIT_FS_SCHEME}
   */
  static void checkScheme(@Nonnull URI uri) throws ProviderMismatchException {
    if(!GitFileSystemProvider.GIT_FS_SCHEME.equalsIgnoreCase(uri.getScheme()))
      throw new ProviderMismatchException(uri.getScheme());
  }

  /**
   * Finds and returns the path to the repository from the given {@code URI}.
   *
   * Within the {@code GitFileSystem} {@code URI} pattern:
   *   git://[repo location]![path in repo (optional)]?[parameters (optional)]
   * this method returns the [repo location] part of the {@code URI}.
   *
   * @param   uri
   *          the {@code URI}
   * @return  the string path to the repository
   */
  @Nonnull
  public static String getRepository(@Nonnull URI uri) {
    checkScheme(uri);
    return uri.getPath();
  }

  /**
   * Finds and returns the path to the file in the repository from the given {@code URI}.
   *
   * Within the {@code GitFileSystem} {@code URI} pattern:
   *   git://[repo location]![file in repo (optional)]?[parameters (optional)]
   * this method returns the [file in repo] part of the {@code URI}. If this part is absent, the default path is "/".
   *
   * @param   uri
   *          the {@code URI}
   * @return  the string path to the file in the repository
   */
  @Nonnull
  public static String getFile(@Nonnull URI uri) throws ProviderMismatchException {
    checkScheme(uri);
    return uri.getFragment();
  }

  @Nonnull
  public static String buildQuery(@Nonnull Map<String, ?> params) {
    String query = "";
    for(Map.Entry<String, ?> param : params.entrySet()) {
      if(!query.isEmpty())
        query += "&";
      query += param.getKey() + "=" + param.getKey();
    }
    return query;
  }

  @Nonnull
  public static Map<String, String> parseQuery(@Nonnull String query, @Nullable Set<String> keys) {
    Map<String, String> params = new HashMap<>();
    String[] pairs = query.split("&");
    int count = 0;
    for(String pair : pairs) {
      String[] keyValue = pair.split("=", 2);
      String key = keyValue[0];
      if(keys == null || keys.contains(key)) {
        params.put(key, keyValue.length > 1 ? keyValue[1] : null);
        if(keys != null && ++count == keys.size())
          break;
      }
    }
    return params;
  }

  @Nonnull
  public static Map<String, String> parseQuery(@Nonnull String query) {
    return parseQuery(query, null);
  }

  @Nullable
  public static String getSession(@Nonnull URI uri) throws ProviderMismatchException {
    checkScheme(uri);
    return parseQuery(uri.getQuery(), Collections.singleton(SID_KEY)).get(SID_KEY);
  }

}
