/*
 * Copyright (c) 2011 Kevin Sawicki <kevinsawicki@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */
package org.gitective.tests;

import java.util.Collection;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepository;
import org.gitective.core.CommitUtils;
import org.junit.Test;

/**
 * Unit tests of {@link CommitUtils}
 */
public class CommitUtilsTest extends GitTestCase {

	/**
	 * Test constructor
	 */
	@Test
	public void constructor() {
		assertNotNull(new CommitUtils() {
		});
	}

	/**
	 * Test getting the commit a tag points to
	 * 
	 * @throws Exception
	 */
	@Test
	public void tagRef() throws Exception {
		add("a.txt", "a");
		RevCommit commit = add("test.txt", "content");
		tag(testRepo, "tag1");
		Repository repo = new FileRepository(testRepo);
		RevCommit refCommit = CommitUtils.getRef(repo, "tag1");
		assertNotNull(refCommit);
		assertEquals(commit, refCommit);
		Collection<RevCommit> commits = CommitUtils.getTags(repo);
		assertNotNull(commits);
		assertEquals(1, commits.size());
		assertEquals(commit, commits.iterator().next());
	}

	/**
	 * Test getting commit for tag that doesn't exist
	 * 
	 * @throws Exception
	 */
	@Test
	public void invalidRef() throws Exception {
		RevCommit commit = CommitUtils.getRef(new FileRepository(testRepo),
				"notatag");
		assertNull(commit);
	}

	/**
	 * Test getting the commit a branch points to
	 * 
	 * @throws Exception
	 */
	@Test
	public void branchRef() throws Exception {
		add("a.txt", "a");
		RevCommit commit = add("test.txt", "content");
		Ref ref = branch(testRepo, "branch1");
		Repository repo = new FileRepository(testRepo);
		RevCommit refCommit = CommitUtils.getRef(repo, ref);
		assertNotNull(refCommit);
		assertEquals(commit, refCommit);
		Collection<RevCommit> commits = CommitUtils.getBranches(repo);
		assertNotNull(commits);
		assertEquals(1, commits.size());
		assertEquals(commit, commits.iterator().next());
	}

	/**
	 * Get commit with null repository parameter
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCommitWithNullRepository() {
		CommitUtils.getCommit(null, ObjectId.zeroId());
	}

	/**
	 * Get commit with null repository parameter
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCommitWithNullRepository2() {
		CommitUtils.getCommit(null, Constants.MASTER);
	}

	/**
	 * Get commit with null object id parameter
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCommitWithNullObjectId() throws Exception {
		CommitUtils.getCommit(new FileRepository(testRepo), (ObjectId) null);
	}

	/**
	 * Get commit with null revision parameter
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCommitWithNullRevision() throws Exception {
		CommitUtils.getCommit(new FileRepository(testRepo), (String) null);
	}

	/**
	 * Get commit with empty revision parameter
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getCommitWithEmptyRevision() throws Exception {
		CommitUtils.getCommit(new FileRepository(testRepo), "");
	}

	/**
	 * Get base with null repository parameter
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithNullRepository() {
		CommitUtils.getBase(null, ObjectId.zeroId());
	}

	/**
	 * Get base with null repository parameter
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithNullRepository2() {
		CommitUtils.getBase(null, Constants.MASTER);
	}

	/**
	 * Get base with null object ids
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithNullIds() throws Exception {
		CommitUtils.getBase(new FileRepository(testRepo), (ObjectId[]) null);
	}

	/**
	 * Get base with empty object ids
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithEmptyIds() throws Exception {
		CommitUtils.getBase(new FileRepository(testRepo), new ObjectId[0]);
	}

	/**
	 * Get base with null revisions
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithNullRevisions() throws Exception {
		CommitUtils.getBase(new FileRepository(testRepo), (String[]) null);
	}

	/**
	 * Get base with empty revision
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBaseWithEmptyRevisions() throws Exception {
		CommitUtils.getBase(new FileRepository(testRepo), new String[0]);
	}

	/**
	 * Get ref with null repository
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getRefWithNullRepository() {
		CommitUtils.getRef(null, Constants.MASTER);
	}

	/**
	 * Get ref with null repository
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getRefWithNullRepository2() {
		CommitUtils.getRef(null, (Ref) null);
	}

	/**
	 * Get ref with null ref name
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getRefWithNullRef() throws Exception {
		CommitUtils.getRef(new FileRepository(testRepo), (Ref) null);
	}

	/**
	 * Get ref with null ref name
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getRefWithNullRefName() throws Exception {
		CommitUtils.getRef(new FileRepository(testRepo), (String) null);
	}

	/**
	 * Get ref with empty ref name
	 * 
	 * @throws Exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getRefWithEmptyRefName() throws Exception {
		CommitUtils.getRef(new FileRepository(testRepo), "");
	}

	/**
	 * Get tags with null repository
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getTagsWithNullRepository() {
		CommitUtils.getTags(null);
	}

	/**
	 * Get branches with null repository
	 */
	@Test(expected = IllegalArgumentException.class)
	public void getBranchesWithNullRepository() {
		CommitUtils.getBranches(null);
	}
}