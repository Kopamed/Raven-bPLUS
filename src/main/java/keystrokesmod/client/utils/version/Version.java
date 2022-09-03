package keystrokesmod.client.utils.version;

import java.util.ArrayList;

public class Version {
    private final String version;
    private final String branchName;
    private final int branchCommit;
    private final ArrayList<Integer> versionNumbers = new ArrayList<>();

    public Version(String version, String branchName, int branchCommit) {
        this.version = version.replace("-", ".");
        this.branchName = branchName;
        this.branchCommit = branchCommit;
        for (String number : this.version.split("\\.")) {
            this.versionNumbers.add(Integer.parseInt(number));
        }
    }

    public String getVersion() {
        return version;
    }

    public String getBranchName() {
        return branchName;
    }

    public int getBranchCommit() {
        return branchCommit;
    }

    public ArrayList<Integer> getVersionNumbers() {
        return versionNumbers;
    }

    public boolean isNewerThan(Version versionToCompare) {
        if (versionToCompare.getVersionNumbers().get(0) < this.getVersionNumbers().get(0)) {
            return true;
        } else if (versionToCompare.getVersionNumbers().get(0) > this.getVersionNumbers().get(0)) {
            return false;
        }
        if (versionToCompare.getVersionNumbers().get(1) < this.getVersionNumbers().get(1)) {
            return true;
        } else if (versionToCompare.getVersionNumbers().get(1) > this.getVersionNumbers().get(1)) {
            return false;
        }
        return versionToCompare.getVersionNumbers().get(2) < this.getVersionNumbers().get(2);
    }

    public boolean equals(Version version) {
        ArrayList<Integer> now = this.getVersionNumbers();
        ArrayList<Integer> nvw = version.getVersionNumbers();

        return now.get(0).equals(nvw.get(0)) && now.get(1).equals(nvw.get(1)) && now.get(2).equals(nvw.get(2));
    }

    @Override
    public String toString() {
        return version + " " + branchName + " " + branchCommit;
    }
}
