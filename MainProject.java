const express = require('express');
const bodyParser = require('body-parser');
const { Octokit } = require('@octokit/rest');

const app = express();
const port = 3000;

// Parse JSON bodies
app.use(bodyParser.json());

// GitHub credentials
const token = process.env.TOKENFORPROJECT;

if (!token) {
  console.error('GitHub token is not set. Please set the tokenforproject environment variable.');
  process.exit(1);
}

const octokit = new Octokit({
  auth: token,
});

// Route to handle incoming item additions
app.post('/add-item', async (req, res) => {
  const item = req.body.item;

  try {
    // Add the item to the GitHub repository
    await addItemToGitHub(item);
    res.status(200).send('Item added to GitHub repository successfully.');
  } catch (error) {
    console.error('Error adding item to GitHub repository:', error);
    res.status(500).send('Failed to add item to GitHub repository.');
  }
});

// Function to add item to GitHub repository
async function addItemToGitHub(item) {
  const owner = 'EpicCodeLearner222';
  const repo = 'codepen-to-github';

  // Create a new file in the repository with the item's content
  await octokit.repos.createOrUpdateFileContents({
    owner,
    repo,
    path: 'items.txt', // Change the filename and path as needed
    message: 'Add item', // Commit message
    content: Buffer.from(item).toString('base64'),
  });
}

// Start the server
app.listen(port, () => {
  console.log(`Server is running on port ${port}`);
});
