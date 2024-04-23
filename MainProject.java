const token = process.env.GITHUB_TOKEN;

if (!token) {
  console.error('GitHub token is not set. Please set the GITHUB_TOKEN environment variable.');
  process.exit(1);
}

// Now you can use the 'token' variable to authenticate with GitHub API
